package com.djohn.springbootmall.Service.Impl;

import com.djohn.springbootmall.Dao.OrderDao;
import com.djohn.springbootmall.Dao.ProductDao;
import com.djohn.springbootmall.Dao.UserDao;
import com.djohn.springbootmall.Dto.BuyItem;
import com.djohn.springbootmall.Dto.CreateOrderRequest;
import com.djohn.springbootmall.Model.Order;
import com.djohn.springbootmall.Model.OrderItem;
import com.djohn.springbootmall.Model.Product;
import com.djohn.springbootmall.Model.User;
import com.djohn.springbootmall.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;

@Component
public class OrderServiceImpl implements OrderService {

    private final  static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final UserDao userDao;

    private final OrderDao orderDao;

    private final ProductDao productDao;

    public OrderServiceImpl(OrderDao orderDao, ProductDao productDao,UserDao userDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        User user = userDao.getUserById(userId);

        if (user == null){
            log.warn("該 userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            //這段是為了拿到商品數據，方便等等計算價錢
            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查product商品是否存在、庫存數否足夠
            if(product == null){
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足，無法購買。剩餘庫存 {}，欲購買數量 {}", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換前端buyItem的數據 to orderItem數據
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        order.setOrderItemList(orderItemList);
        return order;
    }
}

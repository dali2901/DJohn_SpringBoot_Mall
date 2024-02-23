package com.djohn.springbootmall.Service.Impl;

import com.djohn.springbootmall.Dao.OrderDao;
import com.djohn.springbootmall.Dao.ProductDao;
import com.djohn.springbootmall.Dto.BuyItem;
import com.djohn.springbootmall.Dto.CreateOrderRequest;
import com.djohn.springbootmall.Model.OrderItem;
import com.djohn.springbootmall.Model.Product;
import com.djohn.springbootmall.Service.OrderService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    private final ProductDao productDao;

    public OrderServiceImpl(OrderDao orderDao, ProductDao productDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
    }
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            //這段是為了拿到商品數據，方便等等計算價錢
            Product product = productDao.getProductById(buyItem.getProductId());

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
}

package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Dto.OrderQueryParams;
import com.djohn.springbootmall.Model.Order;
import com.djohn.springbootmall.Model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems (Integer orderId, List<OrderItem> orderItemList);
    Order getOrderById(Integer orderId);
    List<OrderItem> getOrderItemsByOrderId(Integer orderId);
    Integer countOrder(OrderQueryParams orderQueryParams);
    List<Order> getOrders(OrderQueryParams orderQueryParams);

}

package com.djohn.springbootmall.Dao;

import com.djohn.springbootmall.Model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems (Integer orderId, List<OrderItem> orderItemList);
}

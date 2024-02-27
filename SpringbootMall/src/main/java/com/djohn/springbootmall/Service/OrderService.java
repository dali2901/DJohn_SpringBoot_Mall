package com.djohn.springbootmall.Service;

import com.djohn.springbootmall.Dto.CreateOrderRequest;
import com.djohn.springbootmall.Model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

}

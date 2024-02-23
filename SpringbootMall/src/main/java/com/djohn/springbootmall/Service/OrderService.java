package com.djohn.springbootmall.Service;

import com.djohn.springbootmall.Dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}

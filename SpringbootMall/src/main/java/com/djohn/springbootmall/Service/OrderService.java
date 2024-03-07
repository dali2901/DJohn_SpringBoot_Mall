package com.djohn.springbootmall.Service;

import com.djohn.springbootmall.Dto.CreateOrderRequest;
import com.djohn.springbootmall.Dto.OrderQueryParams;
import com.djohn.springbootmall.Model.Order;
import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

}

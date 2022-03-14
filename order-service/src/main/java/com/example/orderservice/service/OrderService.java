package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderDetail;
import com.example.orderservice.specification.ObjectFilter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface OrderService {
    OrderDto create(Order order);

    Page<Order> getAll(ObjectFilter obj);

    Order findById(Long orderId);

    Order save(Order orderExist);

    List findOrderByUserId(long userId);

    boolean delete(long id);

    boolean updateStatus(int id, String status);

    Set<OrderDetail> getOrderDetailByOrderId(Integer id);
}

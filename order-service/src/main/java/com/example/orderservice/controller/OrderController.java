package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderRepository repository;

    @RequestMapping
    public List<Order> getAll() {
        return repository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Order create(@RequestBody Order order) {
        try {
            return repository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

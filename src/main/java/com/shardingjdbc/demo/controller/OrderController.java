package com.shardingjdbc.demo.controller;

import com.shardingjdbc.demo.entity.Order;
import com.shardingjdbc.demo.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderMapper orderMapper;


    @GetMapping("/get")
    public Order getOrderById(Long id) {
        return orderMapper.getById(id);
    }
}

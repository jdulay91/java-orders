package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    //http://localhost:2019/orders/order/7
    @GetMapping(value = "/orders/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum){
        Order myOrder = orderService.findOrderById(ordnum);
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }
}

package com.lambdaschool.orders.controllers;


import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    //POST REQ
    //http://localhost:2019/orders/order
    @PostMapping(value = "/orders/order", consumes = "application/json",
            produces =
            "application/json")
    public ResponseEntity<?> addNewOrder(@Valid @RequestBody Order newOrder){
        newOrder = orderService.save(newOrder);
        newOrder.setOrdnum(0);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI =
                ServletUriComponentsBuilder.fromCurrentRequest().path(
                        "/{ordnum}").buildAndExpand(newOrder.getOrdnum()).toUri();
        responseHeaders.setLocation(newOrderURI);
        return new ResponseEntity<>(null,responseHeaders,HttpStatus.CREATED);
    }

    //PUT REQ
    //http://localhost:2019/orders/order/63
    @PutMapping( value = "/orders/order/{ordnum}", consumes = "application" +
            "/json", produces = "application/json")
    public ResponseEntity<?> updateWholeOrder(@PathVariable long ordnum,
                                              @Valid @RequestBody Order updateOrder){
        updateOrder.setOrdnum(ordnum);
        updateOrder = orderService.save(updateOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Delete Req
    //http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/orders/order/{ordnum}")
    public ResponseEntity<?> deleteOrderByNum(@PathVariable long ordnum){
        orderService.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

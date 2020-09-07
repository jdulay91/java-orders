package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "orderService")
public class OrderServiceImplementation implements OrderService {

    @Autowired
    OrdersRepository orderrepos;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderrepos.save(order);
    }

    @Override
    public Order findOrderById(long ordnum) {
        return orderrepos.findById(ordnum).orElseThrow(()-> new EntityNotFoundException("Order number " + ordnum + " Does not Exist"));
    }
}

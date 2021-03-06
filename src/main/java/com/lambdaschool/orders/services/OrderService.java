package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

public interface OrderService {

    Order findOrderById(long ordnum);
    Order save(Order order);
    void delete(long ordnum);
}

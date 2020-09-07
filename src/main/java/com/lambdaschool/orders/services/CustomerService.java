package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAllOrders();
    Customer findCustomerById(long id);
    Customer findCustomerByName(String name);
}

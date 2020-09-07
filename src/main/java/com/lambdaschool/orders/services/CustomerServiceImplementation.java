package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private CustomersRepository custrepos;

    @Override
    public List<Customer> findAllOrders() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;

    }

    @Override
    public Customer findCustomerById(long id) {
        return custrepos.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer with " + id + " Has not been Found"));
    }

    @Override
    public Customer findCustomerByName(String name) {
        return custrepos.findByCustnameContainingIgnoringCase(name);
    }
}

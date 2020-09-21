package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //http://localhost:2019/customers/orders
    @GetMapping ( value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllOrders(){
        List<Customer> myList = customerService.findAllOrders();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/customer/7
    @GetMapping( value ="/customer/{custid}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custid){
        Customer myCustomer = customerService.findCustomerById(custid);
        return new ResponseEntity<>(myCustomer, HttpStatus.OK);
    }

    //http://localhost:2019/customers/customer/77
    //Error handled

    //http://localhost:2019/customers/namelike/mes
    @GetMapping( value = "/namelike/{customername}", produces = "application" +
            "/json")
    public ResponseEntity<?> findCustomerByName(@PathVariable String customername){

        Customer myCustomer = customerService.findCustomerByName(customername);
        return new ResponseEntity<>(myCustomer,HttpStatus.OK);
    }

    //http://localhost:2019/customers/namelike/cin

}

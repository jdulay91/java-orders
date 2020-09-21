package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    //POST
    //http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid @RequestBody Customer newCustomer){
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI =
                ServletUriComponentsBuilder.fromCurrentRequestUri().path("/" + newCustomer.getCustcode()).build().toUri();
        responseHeaders.setLocation(newCustomerURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    //PUT
    //http://localhost:2019/customers/customer/19
    @PutMapping(value = "/customer/{custcode}", consumes =
            "application/json", produces = "application/json")
    public ResponseEntity<?> updateWholeCustomer(@Valid @RequestBody Customer updateCustomer, @PathVariable long custcode){
        updateCustomer.setCustcode(custcode);
        updateCustomer = customerService.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
    //PATCH
    //http://localhost:2019/customers/customer/19
    @PatchMapping(value = "/customer/{custcode}", consumes ="application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomerInfo(@PathVariable long custcode,
                                                @RequestBody Customer updateCustomer){
        updateCustomer = customerService.update(updateCustomer, custcode);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    //DELETE
    //http://localhost:2019/customers/customer/54
    @DeleteMapping(value="/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode){
        customerService.delete(custcode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Transactional
@Service(value = "orderService")
public class OrderServiceImplementation implements OrderService {

    @Autowired
    OrdersRepository orderrepos;

    @Autowired
    CustomersRepository cusrepos;

    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();
        if (order.getOrdnum() != 0) {
            orderrepos.findById(order.getOrdnum()).orElseThrow(() -> new EntityNotFoundException("Order number " + order.getOrdnum() + " Not Valid!!"));
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = new Payment();
            newPayment.setType(p.getType());
            newOrder.getPayments().add(newPayment);
        }

        Customer newCustomer =
                cusrepos.findById(order.getCustomer().getCustcode()).orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " Not Valid"));
        newOrder.setCustomer(newCustomer);
        return orderrepos.save(order);
    }

    @Override
    public Order findOrderById(long ordnum) {
        return orderrepos.findById(ordnum).orElseThrow(() -> new EntityNotFoundException("Order number " + ordnum + " Does not Exist"));
    }

    @Transactional
    @Override
    public void delete(long ordnum) {
        if(orderrepos.findById(ordnum).isPresent()){
            orderrepos.deleteById(ordnum);
        } else {
            throw new EntityNotFoundException("Order num " + ordnum + " Not " +
                    "Valid!");
        }

    }
}


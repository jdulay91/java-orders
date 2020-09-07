package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service( value = "paymentService")
public class PaymentServiceImplementation implements PaymentService{

    @Autowired
    PaymentRepository paymentrepos;

    @Transactional
    @Override
    public Payment save(Payment payment) {
        return paymentrepos.save(payment);
    }
}

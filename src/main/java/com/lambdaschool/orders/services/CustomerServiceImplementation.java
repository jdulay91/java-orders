package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private CustomersRepository custrepos;

    @Autowired
    private AgentsRepository agentrepos;

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

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();
        if(customer.getCustcode() != 0){
            custrepos.findById(customer.getCustcode()).orElseThrow(() -> new EntityNotFoundException("Customer With " + customer.getCustcode() + " Not Found"));
            newCustomer.setCustcode(customer.getCustcode());
        }
        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        Agent newAgent =
                agentrepos.findById(customer.getAgent().getAgentcode()).orElseThrow(()-> new  EntityNotFoundException("Agent with " + customer.getAgent().getAgentcode() + " Not Found "));
        newCustomer.setAgent(newAgent);
        newCustomer.getOrders().clear();
        for(Order o : customer.getOrders()){
            Order newOrder = new Order();
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            for(Payment p : newOrder.getPayments()){
                Payment newPayment = new Payment();
                newPayment.setPaymentid(p.getPaymentid());
                newPayment.setType(p.getType());
                newOrder.getPayments().add(newPayment);
            }
            newOrder.setCustomer(newCustomer);
            newCustomer.getOrders().add(newOrder);
        }
        return custrepos.save(newCustomer);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long custcode) {
        Customer updateCustomer = findCustomerById(custcode);

        if(customer.getCustname() != null){

            updateCustomer.setCustname(customer.getCustname());
        }
        if(customer.getCustcity() != null){

            updateCustomer.setCustcity(customer.getCustcity());
        }
        if(customer.getWorkingarea() != null){

            updateCustomer.setWorkingarea(customer.getWorkingarea());
        }
        if(customer.getCustcountry() != null){
            updateCustomer.setCustcountry(customer.getCustcountry());
        }
        if(customer.getGrade() != null){
            updateCustomer.setGrade(customer.getGrade());
        }
        if(customer.hasvalueforreceiveamt){

            updateCustomer.setReceiveamt(customer.getReceiveamt());
        }
        if(customer.hasvalueforoutstandingamt){

            updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        }
        if(customer.hasvalueforopeningamt){
            updateCustomer.setOpeningamt(customer.getOpeningamt());
        }
        if(customer.hasvalueforpaymentamt){
            updateCustomer.setPaymentamt(customer.getPaymentamt());
        }
        if(customer.getPhone() != null) {
            updateCustomer.setPhone(customer.getPhone());
        }


        Agent newAgent =
                agentrepos.findById(customer.getAgent().getAgentcode()).orElseThrow(()-> new  EntityNotFoundException("Agent with " + customer.getAgent().getAgentcode() + " Not Found "));
        updateCustomer.setAgent(newAgent);
        if(customer.getOrders().size() > 0){

            updateCustomer.getOrders().clear();
            for(Order o : customer.getOrders()){
                Order newOrder = new Order();
                newOrder.setAdvanceamount(o.getAdvanceamount());
                newOrder.setOrdamount(o.getOrdamount());
                newOrder.setOrderdescription(o.getOrderdescription());
                for(Payment p : newOrder.getPayments()){
                    Payment newPayment = new Payment();
                    newPayment.setPaymentid(p.getPaymentid());
                    newPayment.setType(p.getType());
                    newOrder.getPayments().add(newPayment);
                }
                newOrder.setCustomer(updateCustomer);
                updateCustomer.getOrders().add(newOrder);
            }
        }
        return custrepos.save(updateCustomer);
    }

    @Transactional
    @Override
    public void delete(long custcode) {
        if(custrepos.findById(custcode).isPresent()){
            custrepos.deleteById(custcode);
        } else{
            throw new EntityNotFoundException("Customer id of " + custcode +
                    " Not Found!!");
        }
    }
}

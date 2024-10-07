package com.example.content_generator.dataservice.service;


import com.example.content_generator.dataservice.model.Customer;
import com.example.content_generator.dataservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);  // UUID is generated in Customer constructor
    }

    public Customer updateCustomer(String id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setDemographic(customer.getDemographic());
            updatedCustomer.setPhysiographic(customer.getPhysiographic());
            updatedCustomer.setBehavioral(customer.getBehavioral());
            updatedCustomer.setDigitalBehavior(customer.getDigitalBehavior());
            updatedCustomer.setLocation(customer.getLocation());
            return customerRepository.save(updatedCustomer);
        } else {
            return null;
        }
    }

    public void deleteCustomerById(String id) {
        customerRepository.deleteById(id);
    }
}
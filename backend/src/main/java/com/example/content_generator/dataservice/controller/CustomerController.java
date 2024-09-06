package com.example.content_generator.dataservice.controller;

import com.example.content_generator.dataservice.core.DynamicMapper;
import com.example.content_generator.dataservice.dto.CustomerDTO;
import com.example.content_generator.dataservice.model.Customer;
import com.example.content_generator.dataservice.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Customer createCustomer(@RequestBody CustomerDTO customerRTO) {
        return customerService.saveCustomer(DynamicMapper.mapToModel(customerRTO, Customer.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerRTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, DynamicMapper.mapToModel(customerRTO, Customer.class));
        return updatedCustomer != null ? ResponseEntity.ok(updatedCustomer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable String id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.noContent().build();
    }
}

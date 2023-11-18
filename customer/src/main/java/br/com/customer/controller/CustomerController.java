package br.com.customer.controller;

import br.com.customer.model.Customer;
import br.com.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping(value = "/save")
    public ResponseEntity<Customer> save(@RequestBody Customer customer) {
        return ResponseEntity.status(201).body(customerService.save(customer));
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.status(201).body(customerService.findAll());
    }
}

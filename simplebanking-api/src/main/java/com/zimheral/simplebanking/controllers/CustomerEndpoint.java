package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.api.CustomerApi;
import com.zimheral.simplebanking.model.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerEndpoint implements CustomerApi {
    @Override
    public ResponseEntity<Customer> getCustomer() {
        Customer customer = new Customer();
        return ResponseEntity.ok().body(customer);
    }
}

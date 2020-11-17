package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.api.CustomersApi;
import com.zimheral.simplebanking.model.Customer;
import com.zimheral.simplebanking.model.CustomerData;
import com.zimheral.simplebanking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerEndpoint implements CustomersApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerData> getCustomerData(Long customerId) {
        val customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customerService.getData(customer));
    }

    @Override
    public ResponseEntity<List<Customer>> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }
}

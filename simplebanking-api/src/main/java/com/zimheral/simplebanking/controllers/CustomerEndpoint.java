package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.api.CustomerApi;
import com.zimheral.simplebanking.model.CustomerData;
import com.zimheral.simplebanking.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerEndpoint implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerData> getCustomerData(Long customerId) {
        val customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customerService.getData(customer));
    }
}

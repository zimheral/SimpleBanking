package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.entities.Customer;
import com.zimheral.simplebanking.model.CustomerData;
import com.zimheral.simplebanking.services.CustomerService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerEndpointTest {

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerEndpoint customerEndpoint;

    @Test
    void shouldGetCustomerData() {
        //GIVEN
        long customerId = 1L;
        val customer = mock(Customer.class);
        val customerData = mock(CustomerData.class);

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(customerService.getData(customer)).thenReturn(customerData);

        //WHEN
        ResponseEntity<CustomerData> response = customerEndpoint.getCustomerData(customerId);

        //THEN
        assertNotNull(response.getBody());
        assertEquals(customerData, response.getBody());
    }
}
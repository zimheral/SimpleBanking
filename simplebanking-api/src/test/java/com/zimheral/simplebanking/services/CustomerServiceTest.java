package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.entities.Customer;
import com.zimheral.simplebanking.repositories.CustomerRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldGetCustomer() {

        //GIVEN
        long customerId = 123L;
        val customer = mock(Customer.class);
        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerRepository.findById(customerId)).thenReturn(customerOptional);

        //WHEN
        val response = customerService.getCustomer(customerId);

        //THEN
        assertNotNull(response);
        assertEquals(customer, response);
    }

    @Test
    void shouldThrowExceptionWhenGetCustomerNotFound() {
        //GIVEN
        long customerId = 123L;
        Optional<Customer> customerOptional = Optional.empty();
        when(customerRepository.findById(customerId)).thenReturn(customerOptional);

        //THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> customerService.getCustomer(customerId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void shouldGetDataWithoutAccount() {
        //GIVEN
        long customerId = 123L;
        val customer = Customer.builder().id(customerId).name("John").surname("Doe").build();

        //WHEN
        val response = customerService.getData(customer);

        //THEN
        assertNotNull(response);
        assertEquals(customer.getName(), response.getName());
        assertEquals(customer.getSurname(), response.getSurname());
    }

    @Test
    void shouldGetDataWithAccountAndNoTransactions() {
        //GIVEN
        long customerId = 123L;
        val account = Account.builder()
                .iban("BE1234")
                .accountType(com.zimheral.simplebanking.model.Account.AccountTypeEnum.CURRENT_ACCOUNT)
                .balance(BigDecimal.ZERO)
                .build();
        val customer = Customer.builder().id(customerId).name("John").surname("Doe").account(account).build();

        //WHEN
        val response = customerService.getData(customer);

        //THEN
        assertNotNull(response);
        assertEquals(customer.getName(), response.getName());
        assertEquals(customer.getSurname(), response.getSurname());
        assertEquals(response.getAccount().getIban(), response.getAccount().getIban());
        assertEquals(response.getAccount().getIban(), response.getAccount().getIban());
    }
}
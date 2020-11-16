package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.model.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountEndpointTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountEndpoint accountEndpoint;

    @Test
    void shouldOpenAccount() {
        //GIVEN
        long customerId = 1L;
        Credit credit = mock(Credit.class);
        Account account = mock(Account.class);

        when(accountService.processOpenAccount(customerId, credit)).thenReturn(account);

        //WHEN
        ResponseEntity<Account> accountResponse = accountEndpoint.openAccount(customerId, credit);

        //THEN
        assertNotNull(accountResponse.getBody());
        assertEquals(account, accountResponse.getBody());
    }

    @Test
    void shouldThrowExceptionOnOpenAccountWhenCreditNull() {

        //GIVEN
        long customerId = 12345L;

        //THEN
        assertThrows(NullPointerException.class,
                () -> accountEndpoint.openAccount(customerId, null));
    }
}
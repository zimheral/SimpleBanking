package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import com.zimheral.simplebanking.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

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
        CurrentAccount currentAccount = mock(CurrentAccount.class);

        when(accountService.processOpenAccount(customerId, credit)).thenReturn(currentAccount);

        //WHEN
        ResponseEntity<CurrentAccount> currentAccountResponse = accountEndpoint.openAccount(customerId,credit);

        //THEN
        assertNotNull(currentAccountResponse.getBody());
        assertEquals(currentAccount, currentAccountResponse.getBody());
    }

    @Test
    void shouldThrowExceptionOnOpenAccountWhenCreditNull() {

        //GIVEN
        long customerId = 12345L;

        //THEN
        assertThrows(NullPointerException.class,
                () -> accountEndpoint.openAccount(customerId,null));
    }
}
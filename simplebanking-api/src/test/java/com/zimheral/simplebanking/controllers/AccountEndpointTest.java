package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.model.CurrentAccount;
import com.zimheral.simplebanking.services.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountEndpointTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountEndpoint accountEndpoint;

    @Test
    void openAccount() {
        //GIVEN
        long customerId = 1;
        String account = "AC12345";

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccount(account);

        when(accountService.processOpenAccount(customerId, null)).thenReturn(currentAccount);

        //WHEN
        ResponseEntity<CurrentAccount> currentAccountResponse = accountEndpoint.openAccount(customerId,null);

        //THEN
        assertNotNull(currentAccountResponse.getBody());
        assertEquals(account, currentAccountResponse.getBody().getAccount());
    }
}
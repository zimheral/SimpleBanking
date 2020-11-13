package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.api.AccountApi;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public class AccountEndpoint implements AccountApi {
    @Override
    public ResponseEntity<CurrentAccount> openAccount(Long customerId, @Valid Credit body) {
        return null;
    }
}

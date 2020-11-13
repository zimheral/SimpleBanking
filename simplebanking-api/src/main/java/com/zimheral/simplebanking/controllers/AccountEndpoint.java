package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.api.AccountApi;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import com.zimheral.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public class AccountEndpoint implements AccountApi {

    @Autowired
    private final AccountService accountService;

    public AccountEndpoint(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<CurrentAccount> openAccount(Long customerId, @Valid Credit credit) {
        return ResponseEntity.ok(accountService.processOpenAccount(customerId, credit));
    }
}

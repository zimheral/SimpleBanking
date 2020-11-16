package com.zimheral.simplebanking.controllers;

import com.zimheral.simplebanking.api.AccountApi;
import com.zimheral.simplebanking.model.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.services.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountEndpoint implements AccountApi {

    private final AccountService accountService;

    @Override
    public ResponseEntity<Account> openAccount(Long customerId, @NonNull @Valid Credit credit) {
        return ResponseEntity.ok(accountService.processOpenAccount(customerId, credit));
    }
}

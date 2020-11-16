package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.model.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.repositories.AccountRepository;
import com.zimheral.simplebanking.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final TransactionService transactionService;
    private final CustomerService customerService;

    public Account processOpenAccount(final Long customerId, final Credit credit) {
        val customer = customerService.getCustomer(customerId);
        BigDecimal creditAmount = credit.getCredit();
        if (creditAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit for account opening cannot be negative");
        }

        val account = new Account();

        //CASE 1: User already has a current account
        Optional<com.zimheral.simplebanking.entities.Account> accountOptional = repository.findByCustomer(customer);
        if (accountOptional.isPresent()) {
            //Build response from retrieved entity
            ApiUtils.buildAccount(account, accountOptional.get());
            log.info("CustomerId: {},  Retrieved account from DB: {} ", customerId, account.getIban());

            //CASE 2: Generate new account
        } else {
            //Build entity
            com.zimheral.simplebanking.entities.Account accountEntity = ApiUtils.buildAccountEntity(creditAmount, customer);

            //Save entity
            com.zimheral.simplebanking.entities.Account savedAccount = repository.saveAndFlush(accountEntity);

            //Build response from saved entity
            ApiUtils.buildAccount(account, savedAccount);
            log.info("CustomerId: {},  Opened account: {} , Account Type: {}", customerId, account.getIban(), account.getAccountType());

            //CASE 2.1: Account with initial credit
            if (creditAmount.compareTo(BigDecimal.ZERO) > 0) {
                val transaction = transactionService.processTransaction(creditAmount, savedAccount);
                account.setTransactions(ApiUtils.convertTransactions(Collections.singletonList(transaction)));
            }
        }
        return account;
    }
}

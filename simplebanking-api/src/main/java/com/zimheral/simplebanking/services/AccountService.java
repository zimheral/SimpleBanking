package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import com.zimheral.simplebanking.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final TransactionService transactionService;

    public CurrentAccount processOpenAccount(final Long customerId, final Credit credit) {

        if (credit.getCredit().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit for account opening cannot be negative");
        }

        val currentAccount = new CurrentAccount();

        //CASE 1: User already has a current account
        Optional<Account> accountFromDb = repository.findByCustomerId(customerId);
        if (accountFromDb.isPresent() && !Objects.isNull(accountFromDb.get().getCurrentAccount())) {
            currentAccount.setAccount(accountFromDb.get().getCurrentAccount());
            log.info("CustomerId: {},  Retrieved account from DB: {} ", customerId, currentAccount.getAccount());

            //CASE 2: Generate new account
        } else {
            //Generate account
            currentAccount.setAccount(generateBeAccount());
            log.info("CustomerId: {},  Generated account: {} ", customerId, currentAccount.getAccount());

            //Save account to DB
            Account account = Account.builder().customerId(customerId).currentAccount(currentAccount.getAccount()).build();
            Account savedAccount = repository.save(account);

            if (credit.getCredit().compareTo(BigDecimal.ZERO) > 0) {
                transactionService.processTransaction(credit.getCredit(), savedAccount);
            }
        }
        return currentAccount;
    }

    private String generateBeAccount() {
        String accountNumber;
        Random random = new Random();
        accountNumber = IntStream.range(0, 14)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining("", "BE", ""));
        return accountNumber;
    }
}

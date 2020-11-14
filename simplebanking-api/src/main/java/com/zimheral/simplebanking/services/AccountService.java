package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import com.zimheral.simplebanking.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public CurrentAccount processOpenAccount(final Long customerId, final Credit credit) {

        if (Objects.isNull(credit) || credit.getCredit() < 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        final CurrentAccount currentAccount = new CurrentAccount();

        log.info("Processing account for customerId: " + customerId);

        //CASE: Check if User already has a current account
        Optional<Account> account = repository.findByCustomerId(customerId);
        if (account.isPresent() && !Objects.isNull(account.get().getCurrentAccount())) {
            currentAccount.setAccount(account.get().getCurrentAccount());
            log.info("CustomerId: {},  Retrieved account from DB: {} ", customerId, currentAccount.getAccount());

        } else {
            //Generate account
            currentAccount.setAccount(generateBeAccount());
            log.info("CustomerId: {},  Generated account: {} ", customerId, currentAccount.getAccount());

            //Save account to DB
            Account saveAccount = new Account(customerId, currentAccount.getAccount());
            repository.save(saveAccount);

            if (credit.getCredit() > 0) {
                //Implement transaction service and add credit as transaction
                //Ex: transactionService.processTransaction(currentAccount, credit);
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

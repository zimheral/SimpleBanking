package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.model.Credit;
import com.zimheral.simplebanking.model.CurrentAccount;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AccountService {

    public CurrentAccount processOpenAccount(final Long customerId, final Credit credit) {

        if (Objects.isNull(credit) || credit.getCredit() < 0) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
        }

        //CASE: Check if User already has a current account
        //repository.getAccount(customerId)

        //Generate account
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setAccount(generateBeAccount());

        //Save account to DB
        //accountRepository.save(customerId, currentAccount);

        if (credit.getCredit() > 0) {
            //Call transaction service
            //transactionService.processTransaction(currentAccount, credit);
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

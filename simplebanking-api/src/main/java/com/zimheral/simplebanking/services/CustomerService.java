package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Customer;
import com.zimheral.simplebanking.model.Account;
import com.zimheral.simplebanking.model.CustomerData;
import com.zimheral.simplebanking.model.Transaction;
import com.zimheral.simplebanking.repositories.CustomerRepository;
import com.zimheral.simplebanking.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getCustomer(long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided customerId was not found in the database"));
    }

    public CustomerData getData(Customer customer) {
        Account account = null;
        com.zimheral.simplebanking.entities.Account accountDB = customer.getAccount();

        if (Objects.nonNull(customer.getAccount())) {
            account = new Account().iban(accountDB.getIban()).balance(accountDB.getBalance()).accountType(accountDB.getAccountType());
            if (Objects.nonNull(customer.getAccount().getTransactions())) {
                List<Transaction> transactionList = ApiUtils.convertTransactions(customer.getAccount().getTransactions());
                account.setTransactions(transactionList);
            }
        }

        return new CustomerData()
                .name(customer.getName())
                .surname(customer.getSurname())
                .account(account);
    }


}

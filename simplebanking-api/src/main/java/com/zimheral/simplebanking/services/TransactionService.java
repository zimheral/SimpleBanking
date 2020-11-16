package com.zimheral.simplebanking.services;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.entities.Transaction;
import com.zimheral.simplebanking.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction processTransaction(final BigDecimal amount, final Account account) {
        Transaction transaction = Transaction.builder().amount(amount).account(account).build();
        Transaction savedTransaction = transactionRepository.saveAndFlush(transaction);
        log.info("Registered transactionId: {}, amount: {}, customerId: {} ",
                savedTransaction.getId(), savedTransaction.getAmount(), account.getCustomer().getId());
        return savedTransaction;
    }
}

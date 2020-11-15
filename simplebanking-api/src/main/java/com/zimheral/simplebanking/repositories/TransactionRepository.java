package com.zimheral.simplebanking.repositories;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

    List<Transaction> getAllTransactionsByAccount (Account account);
}

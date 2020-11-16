package com.zimheral.simplebanking.repositories;

import com.zimheral.simplebanking.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}

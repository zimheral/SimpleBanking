package com.zimheral.simplebanking.repositories;

import com.zimheral.simplebanking.entities.Account;
import com.zimheral.simplebanking.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByCustomer(Customer customer);
}

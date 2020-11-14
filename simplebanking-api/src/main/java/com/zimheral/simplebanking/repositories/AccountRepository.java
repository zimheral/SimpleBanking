package com.zimheral.simplebanking.repositories;

import com.zimheral.simplebanking.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByCustomerId(Long customerId);
}

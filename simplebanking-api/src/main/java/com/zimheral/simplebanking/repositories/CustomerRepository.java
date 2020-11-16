package com.zimheral.simplebanking.repositories;

import com.zimheral.simplebanking.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findById(Long id);
}

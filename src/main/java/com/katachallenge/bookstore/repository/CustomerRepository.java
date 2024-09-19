package com.katachallenge.bookstore.repository;

import com.katachallenge.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * Repository interface for managing Customer entities.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
}

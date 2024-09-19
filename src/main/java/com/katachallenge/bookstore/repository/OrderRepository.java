package com.katachallenge.bookstore.repository;

import com.katachallenge.bookstore.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository interface for managing CustomerOrder entities.
 */
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
}

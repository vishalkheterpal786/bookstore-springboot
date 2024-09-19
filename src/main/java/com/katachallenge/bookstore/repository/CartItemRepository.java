package com.katachallenge.bookstore.repository;

import com.katachallenge.bookstore.model.CartItem;
import com.katachallenge.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing CartItem entities.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(Customer user);
}

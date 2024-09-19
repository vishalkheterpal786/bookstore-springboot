package com.katachallenge.bookstore.repository;

import com.katachallenge.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Book entities.
 */
public interface BookRepository extends JpaRepository<Book,Long> {
}

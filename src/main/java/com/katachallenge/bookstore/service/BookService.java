package com.katachallenge.bookstore.service;

import com.katachallenge.bookstore.model.Book;
import com.katachallenge.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for handling business logic related to books.
 * This service interacts with the {@link BookRepository} to retrieve book data from the database.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /**
     * @param bookRepository the repository used to interact with the books data in the database.
     */
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves a list of all books available in the database.
     */
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }
}

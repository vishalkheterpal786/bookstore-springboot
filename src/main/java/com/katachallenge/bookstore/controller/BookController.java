package com.katachallenge.bookstore.controller;

import com.katachallenge.bookstore.model.Book;
import com.katachallenge.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for managing book-related operations.
 * This controller provides endpoints to retrieve book data.
 * All endpoints are prefixed with '/api/books'.
 */
@RestController
@RequestMapping(value = "/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Constructor-based dependency injection for BookService.
     *
     * @param bookService the service responsible for book operations.
     */
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Endpoint to retrieve all books from the system.
     * This method handles GET requests to '/api/books/getAllBooks' and
     * returns a list of all available books.
     * @return a list of books from the book service.
     */
    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}

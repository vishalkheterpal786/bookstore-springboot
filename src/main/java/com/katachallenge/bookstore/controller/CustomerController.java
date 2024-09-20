package com.katachallenge.bookstore.controller;

import com.katachallenge.bookstore.model.Customer;
import com.katachallenge.bookstore.service.OrderService;
import com.katachallenge.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * REST Controller for managing customer-related operations such as registration and login.
 * This controller exposes endpoints for customer registration and login.
 * All endpoints are prefixed with '/api/customer'.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final UserService userService;

    /**
     * Constructor-based dependency injection for UserService.
     * @param userService the service responsible for user-related operations like registration and login.
     */
    @Autowired
    public CustomerController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint for customer registration.
     * @param customer the Customer object containing registration details (username, password, etc.).
     * @return registered Customer.
     */
    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer) {
        Customer registerUser = userService.registerUser(customer);
        return new ResponseEntity<>(registerUser, HttpStatus.OK);
    }

    /**
     * Endpoint for customer login.
     * @param customer the Customer object containing login details (username and password).
     * @return ResponseEntity with:
     * - HTTP 200 (OK) if login is successful, along with the JWT token in the response headers.
     * - HTTP 401 (UNAUTHORIZED) if login fails (e.g., invalid credentials).
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Customer customer) {

        String token = userService.loginUser(customer);

        // Create HTTP Headers to include the JWT token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        // Return success message with JWT in headers
        return new ResponseEntity<>("Login successful", headers, HttpStatus.OK);

    }
}
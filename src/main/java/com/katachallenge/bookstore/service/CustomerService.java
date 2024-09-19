package com.katachallenge.bookstore.service;

import com.katachallenge.bookstore.model.Customer;
import com.katachallenge.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Service class that provides user details for authentication and authorization.
 * This service implements Spring Security's {@link UserDetailsService}, which is used
 * to load user-specific data during the authentication process.
 */
@Service
public class CustomerService implements UserDetailsService {

    private final CustomerRepository userRepository;

    @Autowired
    public CustomerService(CustomerRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username and returns a {@link UserDetails} object,
     * which is used by Spring Security for authentication and authorization.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the customer (user) by username, throw exception if not found
        Customer user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create and return a UserDetails object with the user's credentials
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}


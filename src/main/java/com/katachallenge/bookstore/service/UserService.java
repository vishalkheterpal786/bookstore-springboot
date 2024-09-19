package com.katachallenge.bookstore.service;

import com.katachallenge.bookstore.config.JwtUtil;
import com.katachallenge.bookstore.exceptions.CustomerAlreadyExistException;
import com.katachallenge.bookstore.exceptions.UserNotFoundException;
import com.katachallenge.bookstore.model.Customer;
import com.katachallenge.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * Service class for handling user-related operations such as registration and login.
 */
@Service
public class UserService {

    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(CustomerRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user if the username does not already exist in the database.
     *
     * @param customer the customer object containing user details for registration.
     * @return the registered {@link Customer} object.
     * @throws CustomerAlreadyExistException if a user with the same username already exists.
     */
    public Customer registerUser(Customer customer) {
        // Check if the user already exists
        if (userRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new CustomerAlreadyExistException("User already exists with username: " + customer.getUsername());
        }

        // Create and save new user with encoded password
        Customer user = new Customer();
        user.setUsername(customer.getUsername());
        user.setPassword(passwordEncoder.encode(customer.getPassword()));  // Encode the password before saving
        return userRepository.save(user);
    }

    /**
     * Authenticates the user and generates a JWT token upon successful login.
     * <p>
     * This method authenticates the user using provided credentials. If authentication is successful,
     * it generates a JWT token using the {@link JwtUtil} class and sets the authentication context.
     * If authentication fails, it throws a {@link UserNotFoundException}.
     * </p>
     *
     * @param customer the customer object containing login credentials (username and password).
     * @return the generated JWT token.
     * @throws UserNotFoundException if authentication fails.
     */
    public String loginUser(Customer customer) {
        try {
            // Perform authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            customer.getUsername(),
                            customer.getPassword()
                    )
            );

            // Set the authentication context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate and return JWT token
            return jwtUtil.generateToken(authentication);

        } catch (AuthenticationException e) {
            throw new UserNotFoundException("Customer login failed: " + e.getMessage());
        }
    }
}

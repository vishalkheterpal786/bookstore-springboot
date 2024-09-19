package com.katachallenge.bookstore.exceptions;

/**
 * Custom exception class for handling invalid credentials during authentication processes.
 */
public class CustomerAlreadyExistException extends RuntimeException{
    /**
     * Constructs a new InvalidCredentialException with a specified error message.
     * @param message the detail message explaining the reason for the exception.
     */
    public CustomerAlreadyExistException(String message) {
            super(message);
        }
}

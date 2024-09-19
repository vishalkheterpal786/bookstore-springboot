package com.katachallenge.bookstore.exceptions;

/**
 * Custom exception class ResourceNotFoundException.
 */
public class ResourceNotFoundException  extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

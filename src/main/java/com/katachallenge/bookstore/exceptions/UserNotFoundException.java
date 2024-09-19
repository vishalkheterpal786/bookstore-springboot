package com.katachallenge.bookstore.exceptions;

/**
 * Custom exception class UserNotFound. User does not exist in db.
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}

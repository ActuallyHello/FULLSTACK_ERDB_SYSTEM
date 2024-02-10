package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.NotFoundException;

public class UserNotFoundException extends RuntimeException implements NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

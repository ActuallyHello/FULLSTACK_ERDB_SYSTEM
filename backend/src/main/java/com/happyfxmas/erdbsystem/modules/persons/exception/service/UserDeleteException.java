package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class UserDeleteException extends RuntimeException {
    public UserDeleteException(String message) {
        super(message);
    }

    public UserDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

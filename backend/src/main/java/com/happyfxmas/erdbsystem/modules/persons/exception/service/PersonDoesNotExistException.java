package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class PersonDoesNotExistException extends RuntimeException {
    public PersonDoesNotExistException(String message) {
        super(message);
    }

    public PersonDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

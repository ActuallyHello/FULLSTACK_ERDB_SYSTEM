package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class StudentDoesNotExistException extends RuntimeException {
    public StudentDoesNotExistException(String message) {
        super(message);
    }

    public StudentDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

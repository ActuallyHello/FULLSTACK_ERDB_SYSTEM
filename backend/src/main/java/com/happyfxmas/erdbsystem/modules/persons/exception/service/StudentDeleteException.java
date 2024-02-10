package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class StudentDeleteException extends RuntimeException {
    public StudentDeleteException(String message) {
        super(message);
    }

    public StudentDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

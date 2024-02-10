package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class PositionDoesNotExistException extends RuntimeException {
    public PositionDoesNotExistException(String message) {
        super(message);
    }

    public PositionDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

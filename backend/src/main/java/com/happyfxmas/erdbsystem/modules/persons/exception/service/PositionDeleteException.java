package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class PositionDeleteException extends RuntimeException {
    public PositionDeleteException(String message) {
        super(message);
    }

    public PositionDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.tasks.exception.service;

public class ResultCreationException extends RuntimeException {
    public ResultCreationException(String message) {
        super(message);
    }

    public ResultCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.tasks.exception.service;

public class DenormalizeModelCreationException extends RuntimeException {
    public DenormalizeModelCreationException(String message) {
        super(message);
    }

    public DenormalizeModelCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

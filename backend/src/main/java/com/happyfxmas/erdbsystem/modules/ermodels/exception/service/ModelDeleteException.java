package com.happyfxmas.erdbsystem.modules.ermodels.exception.service;

public class ModelDeleteException extends RuntimeException {
    public ModelDeleteException(String message) {
        super(message);
    }

    public ModelDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

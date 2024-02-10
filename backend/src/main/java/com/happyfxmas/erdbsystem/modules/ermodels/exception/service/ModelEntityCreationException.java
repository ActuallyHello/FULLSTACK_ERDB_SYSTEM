package com.happyfxmas.erdbsystem.modules.ermodels.exception.service;

public class ModelEntityCreationException extends RuntimeException {
    public ModelEntityCreationException(String message) {
        super(message);
    }

    public ModelEntityCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

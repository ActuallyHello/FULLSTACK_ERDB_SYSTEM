package com.happyfxmas.erdbsystem.modules.ermodels.exception.service;

public class RelationCreationException extends RuntimeException {
    public RelationCreationException(String message) {
        super(message);
    }

    public RelationCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

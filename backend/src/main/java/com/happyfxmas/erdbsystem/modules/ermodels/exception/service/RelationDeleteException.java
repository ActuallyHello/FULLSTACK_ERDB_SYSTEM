package com.happyfxmas.erdbsystem.modules.ermodels.exception.service;

public class RelationDeleteException extends RuntimeException {
    public RelationDeleteException(String message) {
        super(message);
    }

    public RelationDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

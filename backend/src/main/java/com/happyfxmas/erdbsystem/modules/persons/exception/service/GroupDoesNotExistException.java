package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class GroupDoesNotExistException extends RuntimeException {
    public GroupDoesNotExistException(String message) {
        super(message);
    }

    public GroupDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class GroupDeleteException extends RuntimeException {
    public GroupDeleteException(String message) {
        super(message);
    }

    public GroupDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class PersonCreationException extends RuntimeException {
    public PersonCreationException(String message) {
        super(message);
    }

    public PersonCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

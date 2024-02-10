package com.happyfxmas.erdbsystem.modules.persons.exception.validation;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class PersonValidationException extends RuntimeException implements ServerException {
    public PersonValidationException(String message) {
        super(message);
    }

    public PersonValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

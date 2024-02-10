package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class PersonServerException extends RuntimeException implements ServerException {
    public PersonServerException(String message) {
        super(message);
    }

    public PersonServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class UserServerException extends RuntimeException implements ServerException {
    public UserServerException(String message) {
        super(message);
    }

    public UserServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class GroupServerException extends RuntimeException implements ServerException {
    public GroupServerException(String message) {
        super(message);
    }

    public GroupServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

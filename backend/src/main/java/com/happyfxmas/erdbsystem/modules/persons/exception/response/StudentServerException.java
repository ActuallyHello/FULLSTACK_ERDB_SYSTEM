package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class StudentServerException extends RuntimeException implements ServerException {
    public StudentServerException(String message) {
        super(message);
    }

    public StudentServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

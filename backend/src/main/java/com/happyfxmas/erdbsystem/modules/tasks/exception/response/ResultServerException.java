package com.happyfxmas.erdbsystem.modules.tasks.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class ResultServerException extends RuntimeException implements ServerException {
    public ResultServerException(String message) {
        super(message);
    }

    public ResultServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

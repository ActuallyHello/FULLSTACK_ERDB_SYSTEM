package com.happyfxmas.erdbsystem.modules.tasks.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class TaskServerException extends RuntimeException implements ServerException {
    public TaskServerException(String message) {
        super(message);
    }

    public TaskServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

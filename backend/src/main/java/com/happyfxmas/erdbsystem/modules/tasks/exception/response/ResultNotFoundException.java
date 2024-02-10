package com.happyfxmas.erdbsystem.modules.tasks.exception.response;


import com.happyfxmas.erdbsystem.exceptions.NotFoundException;

public class ResultNotFoundException extends RuntimeException implements NotFoundException {
    public ResultNotFoundException(String message) {
        super(message);
    }

    public ResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

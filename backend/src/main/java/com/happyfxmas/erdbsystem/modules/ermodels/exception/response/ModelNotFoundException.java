package com.happyfxmas.erdbsystem.modules.ermodels.exception.response;


import com.happyfxmas.erdbsystem.exceptions.NotFoundException;

public class ModelNotFoundException extends RuntimeException implements NotFoundException {
    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

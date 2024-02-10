package com.happyfxmas.erdbsystem.modules.ermodels.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class ModelValidationException extends RuntimeException implements ServerException {
    public ModelValidationException(String message) {
        super(message);
    }

    public ModelValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.ermodels.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class ModelServerException extends RuntimeException implements ServerException {

    public ModelServerException(String message) {
        super(message);
    }

    public ModelServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

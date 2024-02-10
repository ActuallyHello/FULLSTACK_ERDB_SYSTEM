package com.happyfxmas.erdbsystem.modules.tasks.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class LoadExternalResourceException extends RuntimeException implements ServerException {
    public LoadExternalResourceException(String message) {
        super(message);
    }

    public LoadExternalResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}

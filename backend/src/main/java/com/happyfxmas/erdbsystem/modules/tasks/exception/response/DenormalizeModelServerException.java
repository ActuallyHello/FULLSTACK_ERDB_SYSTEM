package com.happyfxmas.erdbsystem.modules.tasks.exception.response;

import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class DenormalizeModelServerException extends RuntimeException implements ServerException {
    public DenormalizeModelServerException(String message) {
        super(message);
    }

    public DenormalizeModelServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.ServerException;

public class TeacherServerException extends RuntimeException implements ServerException {
    public TeacherServerException(String message) {
        super(message);
    }

    public TeacherServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.persons.exception.service;

public class TeacherDoesNotExistException extends RuntimeException {
    public TeacherDoesNotExistException(String message) {
        super(message);
    }

    public TeacherDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

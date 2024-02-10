package com.happyfxmas.erdbsystem.modules.persons.exception.response;


import com.happyfxmas.erdbsystem.exceptions.NotFoundException;

public class TeacherNotFoundException extends RuntimeException implements NotFoundException {
    public TeacherNotFoundException(String message) {
        super(message);
    }

    public TeacherNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.happyfxmas.erdbsystem.modules.tasks.exception.response;


import com.happyfxmas.erdbsystem.exceptions.NotFoundException;

public class TaskNotFoundException extends RuntimeException implements NotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

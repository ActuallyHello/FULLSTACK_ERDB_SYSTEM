package com.happyfxmas.erdbsystem.modules.tasks.exception.service;


public class TaskDoesNotExistException extends RuntimeException {
    public TaskDoesNotExistException(String message) {
        super(message);
    }
}

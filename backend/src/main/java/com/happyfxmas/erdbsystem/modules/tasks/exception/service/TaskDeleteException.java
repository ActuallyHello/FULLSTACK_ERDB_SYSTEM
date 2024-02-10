package com.happyfxmas.erdbsystem.modules.tasks.exception.service;

public class TaskDeleteException extends RuntimeException {
    public TaskDeleteException(String message) {
        super(message);
    }

    public TaskDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

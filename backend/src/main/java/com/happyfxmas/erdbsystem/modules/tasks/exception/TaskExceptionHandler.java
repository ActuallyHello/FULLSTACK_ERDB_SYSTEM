package com.happyfxmas.erdbsystem.modules.tasks.exception;

import com.happyfxmas.erdbsystem.exceptions.ExceptionDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.service.ModelDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.TeacherDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.ResultCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.ResultDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskCreationException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.happyfxmas.erdbsystem.modules.tasks")
public class TaskExceptionHandler {
    @ExceptionHandler(
            value = {
                    TaskDoesNotExistException.class,
                    ModelDoesNotExistException.class,
                    ResultDoesNotExistException.class,
                    TeacherDoesNotExistException.class,
                    StudentDoesNotExistException.class
            }
    )
    public ResponseEntity<Object> handleNotFoundException(RuntimeException notFoundException) {
        var taskException = new ExceptionDTO(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(taskException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    TaskCreationException.class,
                    ResultCreationException.class
            }
    )
    public ResponseEntity<Object> handleServerException(RuntimeException serverException) {
        var taskException = new ExceptionDTO(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(taskException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

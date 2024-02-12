package com.happyfxmas.erdbsystem.modules.ermodels.exception;

import com.happyfxmas.erdbsystem.exceptions.ExceptionDTO;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.response.ModelValidationException;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.service.ModelCreationException;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.service.ModelDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PersonDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.service.TaskDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice(basePackages = "com.happyfxmas.erdbsystem.modules.ermodels")
public class ModelExceptionHandler {

    @ExceptionHandler(
            value = {
                    ModelDoesNotExistException.class,
                    PersonDoesNotExistException.class,
                    TaskDoesNotExistException.class
            }
    )
    public ResponseEntity<Object> handleNotFoundException(RuntimeException notFoundException) {
        var modelException = new ExceptionDTO(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    ModelCreationException.class,
                    ModelValidationException.class
            }
    )
    public ResponseEntity<Object> handleServerException(RuntimeException serverException) {
        var modelException = new ExceptionDTO(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

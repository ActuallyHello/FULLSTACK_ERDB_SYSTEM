package com.happyfxmas.erdbsystem.modules.tasks.exception;

import com.happyfxmas.erdbsystem.exceptions.ExceptionDTO;
import com.happyfxmas.erdbsystem.exceptions.NotFoundException;
import com.happyfxmas.erdbsystem.exceptions.ServerException;
import com.happyfxmas.erdbsystem.modules.ermodels.exception.response.ModelNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.StudentNotFoundException;
import com.happyfxmas.erdbsystem.modules.persons.exception.response.TeacherNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.ResultNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.ResultServerException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.TaskNotFoundException;
import com.happyfxmas.erdbsystem.modules.tasks.exception.response.TaskServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.ustu.erdbsystem.tasks.api.controller")
public class TaskExceptionHandler {
    @ExceptionHandler(
            value = {
                    TaskNotFoundException.class,
                    ModelNotFoundException.class,
                    ResultNotFoundException.class,
                    TeacherNotFoundException.class,
                    StudentNotFoundException.class
            }
    )
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
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
                    TaskServerException.class,
                    ResultServerException.class
            }
    )
    public ResponseEntity<Object> handleServerException(ServerException serverException) {
        var taskException = new ExceptionDTO(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(taskException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(
            value = {
                    MethodArgumentNotValidException.class
            }
    )
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, List<String>> errorMap = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                        )
                );
        var modelException = new ExceptionDTO(
                errorMap,
                methodArgumentNotValidException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.BAD_REQUEST);
    }
}

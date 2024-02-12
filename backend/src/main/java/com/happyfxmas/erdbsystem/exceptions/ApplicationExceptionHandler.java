package com.happyfxmas.erdbsystem.exceptions;

import com.happyfxmas.erdbsystem.modules.ermodels.exception.validation.EnumValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.happyfxmas.erdbsystem")
public class ApplicationExceptionHandler {

    //    @ExceptionHandler(
//            value = {
//                    Exception.class, // TODO LoadTestDataException
//            }
//    )
//    public ResponseEntity<Object> handleException(Exception exception) {
//        var exceptionDTO = new ExceptionDTO(
//                "Internal server error",
//                exception.getClass().getName(),
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                HttpStatus.INTERNAL_SERVER_ERROR.value()
//        );
//        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(
            value = {
                    EnumValueException.class,
            }
    )
    public ResponseEntity<Object> handleEnumValidationException(RuntimeException runtimeException) {
        var enumException = new ExceptionDTO(
                runtimeException.getMessage(),
                runtimeException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(enumException, HttpStatus.INTERNAL_SERVER_ERROR);
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

package com.happyfxmas.erdbsystem.modules.persons.exception;

import com.happyfxmas.erdbsystem.exceptions.ExceptionDTO;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.GroupCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.GroupDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PersonCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PersonDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.PositionDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.StudentDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.TeacherCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.TeacherDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.UserCreationException;
import com.happyfxmas.erdbsystem.modules.persons.exception.service.UserDoesNotExistException;
import com.happyfxmas.erdbsystem.modules.persons.exception.validation.PersonValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.happyfxmas.erdbsystem.modules.persons")
public class PersonExceptionHandler {

    @ExceptionHandler(
            value = {
                    PersonDoesNotExistException.class,
                    UserDoesNotExistException.class,
                    GroupDoesNotExistException.class,
                    PositionDoesNotExistException.class,
                    StudentDoesNotExistException.class,
                    TeacherDoesNotExistException.class,
            }
    )
    public ResponseEntity<Object> handleNotFoundException(
            RuntimeException notFoundException) {
        var personException = new ExceptionDTO(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(personException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    PersonCreationException.class,
                    PersonValidationException.class,
                    UserCreationException.class,
                    GroupCreationException.class,
                    PositionCreationException.class,
                    StudentCreationException.class,
                    TeacherCreationException.class,
            }
    )
    public ResponseEntity<Object> handleServerException(RuntimeException serverException) {
        var personException = new ExceptionDTO(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(personException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

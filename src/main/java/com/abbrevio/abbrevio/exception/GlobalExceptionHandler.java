package com.abbrevio.abbrevio.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomError> handleAuthExceptions(CustomAuthException e) {
        CustomError err = new CustomError();
        err.getMessages().add(e.getMessage());
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e) {
        CustomError err = new CustomError();
        e.getAllErrors().stream().forEach((message) -> err.getMessages().add(message.getDefaultMessage()));
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleResourceNotFoundExceptions(EntityNotFoundException e) {
        CustomError err = new CustomError();
        err.getMessages().add(e.getMessage());
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}

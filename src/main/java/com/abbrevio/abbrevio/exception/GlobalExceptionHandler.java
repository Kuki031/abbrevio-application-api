package com.abbrevio.abbrevio.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.SignatureException;
import java.sql.SQLIntegrityConstraintViolationException;
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

    @ExceptionHandler
    public ResponseEntity<CustomError> handleCustomNotFoundException(CustomNotFoundException e)
    {
        CustomError err = new CustomError();
        err.getMessages().add(String.format("%s with %s:%s does not exist", e.getEntity().getSimpleName(), e.getParameter(), e.getValue()));
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleDuplicateEntriesExceptions(SQLIntegrityConstraintViolationException e)
    {
        CustomError err = new CustomError();
        err.getMessages().add(e.getMessage());
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e)
    {
        CustomError err = new CustomError();
        String modifiedErrorMsg = e.getMessage().substring(0, e.getMessage().indexOf(":"));
        err.getMessages().add(modifiedErrorMsg);
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleDefaultException(Exception e)
    {
        CustomError err = new CustomError();
        err.getMessages().add(e.getMessage());
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomError> handleSignatureException(SignatureException e)
    {
        CustomError err = new CustomError();
        err.getMessages().add(e.getMessage());
        err.setTimestamp(new Date());
        err.setStatusCode(HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }
}

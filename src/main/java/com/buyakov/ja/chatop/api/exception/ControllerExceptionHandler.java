package com.buyakov.ja.chatop.api.exception;

import io.jsonwebtoken.io.IOException;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({
            ResourceNotFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthentificationException(AuthException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(PermissionDeniedDataAccessException.class)
    public ResponseEntity<?> handlePermissionDeniedDataAccessException(PermissionDeniedDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<?> handleDataConflictException(DataConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({
            Exception.class,
            IOException.class
    })
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }
}

package com.m.interview.interview.exception.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler for custom and default validators, e.g. @DroneValidator, @EnumValidator, Length(), Min(), etc
 */
@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
        return new ResponseEntity<>("Error occurred, request validation failed! " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

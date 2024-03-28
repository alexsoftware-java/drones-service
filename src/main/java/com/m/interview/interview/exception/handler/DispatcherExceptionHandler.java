package com.m.interview.interview.exception.handler;

import com.m.interview.interview.exception.DispatcherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler for custom DispatcherException
 */
@Slf4j
@ControllerAdvice
public class DispatcherExceptionHandler {
    @ExceptionHandler(DispatcherException.class)
    public ResponseEntity<String> handleDispatcherException(DispatcherException ex) {
        log.warn("Dispatcher exception occurred %s".formatted(ex.getMessage()), ex);
        return new ResponseEntity<>("Dispatcher can't process your request: "+ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

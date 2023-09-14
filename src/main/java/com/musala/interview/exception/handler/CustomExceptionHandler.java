package com.musala.interview.exception.handler;

import com.musala.interview.exception.DispatcherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(DispatcherException.class)
    public void handleDispatcherException(DispatcherException ex) {
        log.warn("Dispatcher exception occurred %s".formatted(ex.getMessage()), ex);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Dispatcher can't proceed your request");
    }
}

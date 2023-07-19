package com.example.demo.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(LoginFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String loginFailExceptionHandler(LoginFailException e){
        return e.getMessage();
    }
}

package com.example.demo.config.exception;

public class LoginFailException extends RuntimeException{
    public LoginFailException(String message) {
        super(message);
    }
}

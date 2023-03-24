package com.example.demo.exception;

public class InvalidRequestBodyException extends RuntimeException{


    public InvalidRequestBodyException(String message) {
        super(message);
    }
}

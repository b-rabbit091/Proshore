package com.example.demo.exception;


public class InvalidPostCodeRangeException extends RuntimeException {


    public InvalidPostCodeRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPostCodeRangeException(String message) {
        super(message);
    }
}


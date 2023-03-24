package com.example.demo.exception;

public class BatteryWithNameExistException extends  RuntimeException {
    public BatteryWithNameExistException(String message) {
        super(message);
    }
}

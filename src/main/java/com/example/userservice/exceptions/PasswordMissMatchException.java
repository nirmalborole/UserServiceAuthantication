package com.example.userservice.exceptions;

public class PasswordMissMatchException extends Exception{
    public PasswordMissMatchException(String message) {
        super(message);
    }
}

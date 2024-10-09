package com.example.userservice.exceptions;

public class ExpairedTokenException extends Exception{
    public ExpairedTokenException(String message) {
        super(message);
    }
}

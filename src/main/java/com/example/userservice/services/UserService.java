package com.example.userservice.services;

import com.example.userservice.exceptions.ExpairedTokenException;
import com.example.userservice.exceptions.InvalideTokenexception;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;

public interface UserService {

    public User signUp(String name, String email, String password) throws Exception;

    public Token logIn(String email, String password) throws Exception;

    public Token validateToken(String value)throws InvalideTokenexception, ExpairedTokenException;

    public void logOut(String token) throws Exception;
}

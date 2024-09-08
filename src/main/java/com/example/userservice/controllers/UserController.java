package com.example.userservice.controllers;

import com.example.userservice.dtos.LogInRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.ValidateTokenRequestDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody SignUpRequestDto requestDto){
        try {
            User user = this.userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());
            return new ResponseEntity<>(user, HttpStatusCode.valueOf(201));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/logIn")
    public ResponseEntity<Token>logIn(@RequestBody LogInRequestDto requestDto){
        return null;
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Token> validateToken(@RequestBody ValidateTokenRequestDto requestDto){
        return null;
    }
}

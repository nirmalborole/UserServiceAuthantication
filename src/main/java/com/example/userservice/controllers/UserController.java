package com.example.userservice.controllers;

import com.example.userservice.dtos.LogInRequestDto;
import com.example.userservice.dtos.LogOutRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.ValidateTokenRequestDto;
import com.example.userservice.exceptions.ExpairedTokenException;
import com.example.userservice.exceptions.InvalideTokenexception;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

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
        try{
            Token token = this.userService.logIn(requestDto.getEmail(), requestDto.getPassword());
            return new ResponseEntity<>(token,HttpStatusCode.valueOf(200));
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatusCode.valueOf(400));
        }
    }

    @PostMapping("/logOut")
    public ResponseEntity<Void>logOut(@RequestBody LogOutRequestDto requestDto){
        try{
            this.userService.logOut(requestDto.getToken());
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Token> validateToken(@RequestBody ValidateTokenRequestDto requestDto){
        try{
            Token token = this.userService.validateToken(requestDto.getToken());
            return new ResponseEntity<>(token,HttpStatusCode.valueOf(200));
        }catch (ExpairedTokenException ete){
            return new ResponseEntity<>(HttpStatusCode.valueOf(401));
        }catch (InvalideTokenexception ite){
            return new ResponseEntity<>(HttpStatusCode.valueOf(400));
        }

    }
}

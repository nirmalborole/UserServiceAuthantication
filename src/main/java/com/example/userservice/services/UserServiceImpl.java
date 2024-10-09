package com.example.userservice.services;

import com.example.userservice.exceptions.ExpairedTokenException;
import com.example.userservice.exceptions.InvalideTokenexception;
import com.example.userservice.exceptions.PasswordMissMatchException;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, TokenRepository tokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository=tokenRepository;
    }




    @Override
    public User signUp(String name, String email, String password) throws Exception {
        Optional<User> optionalUser = this.userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            throw new Exception("user is already present");
        }

        String encodedpassword = this.bCryptPasswordEncoder.encode(password);

        User user=new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedpassword);

        return this.userRepository.save(user);
    }

    @Override
    public Token logIn(String email, String password) throws Exception {
        Optional<User> optionalUser = this.userRepository.findUserByEmail(email);

        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean matches = this.bCryptPasswordEncoder.matches(password, user.getPassword());

        if(matches){
            String value = RandomStringUtils.randomAlphanumeric(180);
            Calendar c= Calendar.getInstance();
            c.add(Calendar.DATE,30);
            Date time = c.getTime();

            Token token= new Token();
            token.setUser(user);
            token.setValue(value);
            token.setExpireAt(time);
            token.setActive(true);
            return this.tokenRepository.save(token);
        }else{
            throw new PasswordMissMatchException("password not match");
        }
    }

    @Override
    public Token validateToken(String value) throws InvalideTokenexception,ExpairedTokenException {
        Optional<Token> tokenByValue = this.tokenRepository.findTokenByValue(value);
        Token token = tokenByValue.orElseThrow(() -> new InvalideTokenexception("please use a valid token"));

        Date expireAt = token.getExpireAt();
        Date now = new Date();
        if(now.after(expireAt) || !token.isActive()){
            throw new ExpairedTokenException("The token is expired");
        }
        return null;
    }

    @Override
    public void logOut(String value) throws Exception {
        Optional<Token> tokenByValue = this.tokenRepository.findTokenByValue(value);
        Token token = tokenByValue.orElseThrow(() -> new InvalideTokenexception("Please use a valid token"));

        if(token.isActive()){
            token.setActive(false);
            this.tokenRepository.save(token);
        }
    }
}

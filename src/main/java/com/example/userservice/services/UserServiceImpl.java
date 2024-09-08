package com.example.userservice.services;

import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
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
}

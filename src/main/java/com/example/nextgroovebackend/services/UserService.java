package com.example.nextgroovebackend.services;

import com.example.nextgroovebackend.exceptions.InformationExistsException;
import com.example.nextgroovebackend.models.User;
import com.example.nextgroovebackend.repositories.UserProfileRepository;
import com.example.nextgroovebackend.repositories.UserRepository;
import com.example.nextgroovebackend.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;

    @Autowired
    private void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserProfileRepository(UserProfileRepository userProfileRepository){
        this.userProfileRepository = userProfileRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JWTUtils jwtUtils;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public User createUser(User userObject) {
        System.out.println("User service calling create user");
        Optional<User> user = userRepository.findByEmail(userObject.getEmail());
        if (user.isPresent()){
            throw new InformationExistsException("User already registered under " + userObject.getEmail());
        } else {
            userObject.setPassword(passwordEncoder.encode(userObject.getPassword()));
            return userRepository.save(userObject);
        }
    }

    public ResponseEntity<Object> loginUser(Login loginRequest) {
        System.out.println("User service calling loginUser");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.get))
        }
    }

}

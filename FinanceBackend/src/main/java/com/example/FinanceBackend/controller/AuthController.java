package com.example.FinanceBackend.controller;

import com.example.FinanceBackend.model.User;
import com.example.FinanceBackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    // private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService /*, JwtTokenProvider jwtTokenProvider*/) {
        this.userService = userService;
        // this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody User user) {
        Optional<User> authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        if (authenticatedUser.isPresent()) {
            // String jwt = jwtTokenProvider.generateToken(authenticatedUser.get());
            // return new ResponseEntity<>(new AuthResponse(jwt, authenticatedUser.get().getId()), HttpStatus.OK);
            return new ResponseEntity<>(authenticatedUser.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Credenciais inválidas!", HttpStatus.UNAUTHORIZED);
    }
}


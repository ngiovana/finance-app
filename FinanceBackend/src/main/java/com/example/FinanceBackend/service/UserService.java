package com.example.FinanceBackend.service;

import com.example.FinanceBackend.model.User;
import com.example.FinanceBackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new RuntimeException("Username já existe!");
//        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já existe!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}


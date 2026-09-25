package com.bidvelocity.auth.service;

import com.bidvelocity.auth.entity.User;
import com.bidvelocity.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String password) {
        if (repository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return repository.save(new User(username, passwordEncoder.encode(password)));
    }

    public User authenticate(String username, String password) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return user;
    }
}

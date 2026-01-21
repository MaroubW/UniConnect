package com.example.auth.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User register(RegisterRequest req) {

        String username = req.getUsername();
        String email = req.getEmail();

        if (repo.existsByUsername(username)) {
            throw new RuntimeException("Username already used");
        }

        if (repo.existsByEmail(email)) {
            throw new RuntimeException("Email already used");
        }

        Role role = req.getRole();
        if (role == null) {
            role = Role.STUDENT;
        }

        User u = new User();
        u.setUsername(username);
        u.setEmail(email);
        u.setPassword(encoder.encode(req.getPassword()));

        u.setFirstName(req.getFirstName());
        u.setLastName(req.getLastName());

        u.setRole(role);
        u.setEnabled(req.isEnabled());

        return repo.save(u);
    }
}

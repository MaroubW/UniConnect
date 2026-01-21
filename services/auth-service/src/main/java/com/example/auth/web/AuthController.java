package com.example.auth.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.security.JwtUtil;
import com.example.auth.user.RegisterRequest;
import com.example.auth.user.User;
import com.example.auth.user.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(
        origins = {
                "http://127.0.0.1:5500",
                "http://localhost:5500"
        }
)
public class AuthController {

    private final UserService service;
    private final AuthenticationManager manager;
    private final JwtUtil jwt;

    public AuthController(UserService service, AuthenticationManager manager, JwtUtil jwt) {
        this.service = service;
        this.manager = manager;
        this.jwt = jwt;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {

        User user = service.register(req);
        String token = jwt.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        Authentication auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        String username = auth.getName();
        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String token = jwt.generateToken(username, role);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        return ResponseEntity.ok(auth.getName());
    }
}

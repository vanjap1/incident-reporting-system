package net.etfbl.pisio.userservice.controllers;

import net.etfbl.pisio.userservice.dto.JwtResponse;
import net.etfbl.pisio.userservice.dto.LoginRequest;
import net.etfbl.pisio.userservice.dto.UserRequest;
import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        User saved = authService.register(request);
        return ResponseEntity.status(201).body(saved);
    }
}

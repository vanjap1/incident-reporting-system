package net.etfbl.pisio.userservice.services;

import jakarta.validation.Valid;
import net.etfbl.pisio.userservice.dto.LoginRequest;
import net.etfbl.pisio.userservice.dto.UserRequest;
import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.security.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginRequest request) {
        User user = userService.getUserByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user);
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    public User register(UserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        request.setRole(User.Role.USER);
        return userService.createUser(request);
    }

    public User oauth2Login(@Valid String email) {
        return userService.findOrCreateByEmail(email);
    }
}

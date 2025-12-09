package net.etfbl.pisio.userservice.controllers;


import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.repositories.UserRepository;
import net.etfbl.pisio.userservice.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {
    private final AuthService authService;

    public InternalUserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/find-or-create")
    public ResponseEntity<User> findOrCreate(@RequestBody String email) {
        return ResponseEntity.ok(authService.oauth2Login(email));
    }
}

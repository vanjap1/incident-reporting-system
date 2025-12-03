package net.etfbl.pisio.userservice.controllers;


import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.repositories.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
public class InternalUserController {
    private final UserRepository userRepository;

    public InternalUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/find-or-create")
    public User findOrCreate(@RequestBody String email) {
        return userRepository.findByEmail(email)
                .orElseGet(() -> userRepository.save(
                        new User(null, null, email, User.Role.USER, User.Provider.GOOGLE)
                ));
    }
}

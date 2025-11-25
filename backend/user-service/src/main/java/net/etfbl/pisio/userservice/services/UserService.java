package net.etfbl.pisio.userservice.services;

import net.etfbl.pisio.userservice.dto.UserRequest;
import net.etfbl.pisio.userservice.exceptions.DuplicateUserException;
import net.etfbl.pisio.userservice.exceptions.ResourceNotFoundException;
import net.etfbl.pisio.userservice.mappers.UserMapper;
import net.etfbl.pisio.userservice.models.User;
import net.etfbl.pisio.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateUserException("Username already taken: " + userRequest.getUsername());
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateUserException("Email already registered: " + userRequest.getEmail());
        }
        User user = userMapper.toEntity(userRequest);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(existing);
    }

    public User updateUser(Long id, UserRequest userRequest) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (userRepository.existsByUsernameAndIdNot(userRequest.getUsername(),id)) {
            throw new DuplicateUserException("Username already taken: " + userRequest.getUsername());
        }
        if (userRepository.existsByEmailAndIdNot(userRequest.getEmail(),id)) {
            throw new DuplicateUserException("Email already registered: " + userRequest.getEmail());
        }

        existing.setUsername(userRequest.getUsername());
        existing.setPassword(userRequest.getPassword());
        existing.setEmail(userRequest.getEmail());
        existing.setProvider(userRequest.getProvider());
        existing.setProviderId(userRequest.getProviderId());
        existing.setRoles(userRequest.getRoles());

        return userRepository.save(existing);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }
}

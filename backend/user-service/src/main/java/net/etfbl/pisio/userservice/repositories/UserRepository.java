package net.etfbl.pisio.userservice.repositories;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import net.etfbl.pisio.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByUsername(@NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String username);
}

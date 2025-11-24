package net.etfbl.pisio.userservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "provider")
    private String provider;     // e.g. "google"

    @Column(name = "provider_id")
    private String providerId;   // external ID from OAuth provider

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"user_id", "role"})
            }
    )
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();


    public enum Role {
        ADMIN,
        MODERATOR,
        USER
    }
}

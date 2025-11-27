package net.etfbl.pisio.userservice.models;

import jakarta.persistence.*;
import lombok.*;


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

    @Column(unique = true)
    private String username;

    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider = Provider.LOCAL;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean deleted = false;

    public User(String username, String password, String email, Role role, Provider provider) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
    }

    public enum Role {
        USER, MODERATOR, ADMIN
    }

    public enum Provider {
        GOOGLE, LOCAL
    }
}


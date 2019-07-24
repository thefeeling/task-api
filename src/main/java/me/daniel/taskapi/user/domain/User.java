package me.daniel.taskapi.user.domain;

import lombok.*;
import me.daniel.taskapi.user.exception.NotEqualsPasswordException;
import me.daniel.taskapi.user.util.PasswordUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "email"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Column(name = "email", length = 30, nullable = false, unique = true)
    private String email;

    @Column(name = "hash", columnDefinition = "TEXT", nullable = false)
    private String hash;

    @Column(name = "salt", columnDefinition = "TEXT", nullable = false)
    private String salt;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    public User verifyPassword(String providedPassword) {
        if (!PasswordUtils.verifyHashEqual(providedPassword, this.hash, this.salt)) {
            throw new NotEqualsPasswordException();
        }
        return this;
    }

    public static User create(String email, String password) {
        User user = new User();
        user.email = email;
        user.salt = PasswordUtils.generateSaltString(32);
        user.hash = PasswordUtils.generateSecurePassword(password, user.salt);
        return user;
    }

}

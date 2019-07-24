package me.daniel.taskapi.user.domain;

import lombok.*;
import me.daniel.taskapi.global.auth.AuthType;
import me.daniel.taskapi.global.model.user.UserId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO: INDEX SearchHistory
 */
@Entity
@Table(name = "user_login_events")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "userId", "authType"})
public class UserLoginEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides(
        value = @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    )
    private UserId userId;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    public static UserLoginEvent of(UserId userId, AuthType authType) {
        UserLoginEvent evt = new UserLoginEvent();
        evt.userId = userId;
        evt.authType = authType;
        return evt;
    }

}

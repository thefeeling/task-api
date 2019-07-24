package me.daniel.taskapi.global.model.user;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@ToString(of = {"value"})
public class UserId {
    private Long value;
}

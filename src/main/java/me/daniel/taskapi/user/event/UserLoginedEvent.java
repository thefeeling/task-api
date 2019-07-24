package me.daniel.taskapi.user.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.daniel.taskapi.global.auth.AuthType;
import me.daniel.taskapi.global.model.user.UserId;

@Getter
@RequiredArgsConstructor
@ToString
public class UserLoginedEvent {
    private final UserId userId;
    private final AuthType authType;
}

package me.daniel.taskapi.user.application;

import me.daniel.taskapi.global.auth.TokenPayload;
import org.springframework.data.util.Pair;

import java.time.LocalDateTime;

public interface TokenPublishService {
    Pair<LocalDateTime, String> publish(TokenPayload payload);
}

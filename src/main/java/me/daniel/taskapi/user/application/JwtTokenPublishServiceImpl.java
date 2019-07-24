package me.daniel.taskapi.user.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.global.auth.JwtMetaProperties;
import me.daniel.taskapi.global.auth.TokenPayload;
import me.daniel.taskapi.user.exception.FailureTokenCreationException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenPublishServiceImpl implements TokenPublishService {

    private final JwtMetaProperties jwtMetaProperties;

    @Override
    public Pair<LocalDateTime, String> publish(TokenPayload payload) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plusSeconds(jwtMetaProperties.getExpireSeconds());
        try {
            return Pair.of(expiredAt, JWT.create()
                    .withClaim("id", payload.getId())
                    .withClaim("authType", payload.getAuthType().name())
                    .withIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                    .withIssuer(jwtMetaProperties.getIssuer())
                    .withExpiresAt(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
                    .sign(Algorithm.HMAC256(jwtMetaProperties.getSecret())));
        } catch (Throwable e) {
            throw new FailureTokenCreationException();
        }
    }
}

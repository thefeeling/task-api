package me.daniel.taskapi.global.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenPayload {
    private Long id = 0L;
    private AuthType authType = AuthType.LOCAL;

    public static TokenPayload fromDecodedJWT(DecodedJWT jwt) {
        return new TokenPayload(
            jwt.getClaim("id").asLong(),
            AuthType.valueOf(
                jwt.getClaim("authType").asString()
            )
        );
    }

}

package me.daniel.taskapi.global.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.user.exception.FailureAuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenDecodeService implements TokenDecodeService {

    private final JwtMetaProperties jwtMetaProperties;

    @Override
    public TokenPayload decode(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(jwtMetaProperties.getSecret()))
                    .withIssuer(jwtMetaProperties.getIssuer())
                    .build()
                    .verify(token);
            return TokenPayload.fromDecodedJWT(jwt);
        } catch (TokenExpiredException e) {
            throw new ExpiredTokenException();
        } catch (SignatureVerificationException e) {
            throw new InvalidTokenException();
        } catch (Throwable e) {
            throw new FailureAuthenticationException();
        }
    }

    @Override
    public Boolean verify(String token) {
        return this.decode(token) == null ? Boolean.FALSE : Boolean.TRUE;
    }
}

package me.daniel.taskapi.global.auth;

public interface TokenDecodeService {
    Boolean verify(String token);
    TokenPayload decode(String token);
}

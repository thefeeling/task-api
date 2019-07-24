package me.daniel.taskapi.global.auth;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

public class AuthHelper {

    private static final String PAYLOAD = "payload";

    public static TokenPayload getCurrentTokenPayload() {
        return (TokenPayload) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute(PAYLOAD, RequestAttributes.SCOPE_REQUEST);
    }

}

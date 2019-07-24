package me.daniel.taskapi.global.auth;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends BaseException {

    private static String msg = "만료된 인증 토큰입니다.";
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static String code = "EXPIRED_TOKEN";

    public ExpiredTokenException() {
        this(msg, status, code);
    }

    public ExpiredTokenException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

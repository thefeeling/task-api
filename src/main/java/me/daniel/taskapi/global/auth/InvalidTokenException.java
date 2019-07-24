package me.daniel.taskapi.global.auth;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {
    private static String msg = "유효하지 않은 토큰입니다.";
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static String code = "INVALID_TOKEN";

    public InvalidTokenException() {
        this(msg, status, code);
    }

    public InvalidTokenException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

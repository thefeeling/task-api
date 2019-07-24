package me.daniel.taskapi.global.auth;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class EmptyTokenException extends BaseException {

    private static String msg = "인증 토큰은 필수입니다.";
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static String code = "EMPTY_TOKEN";

    public EmptyTokenException() {
        this(msg, status, code);
    }

    public EmptyTokenException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}
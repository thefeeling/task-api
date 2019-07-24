package me.daniel.taskapi.global.auth;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidTokenTypeException  extends BaseException {

    private static String msg = "토큰 타입은 `Bearer` 입니다.";
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static String code = "INVALID_TOKEN_TYPE";

    public InvalidTokenTypeException() {
        this(msg, status, code);
    }

    public InvalidTokenTypeException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class FailureAuthenticationException extends BaseException {
    private static String msg = "인증에 실패했습니다.";
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static String code = "FAILURE_AUTHENTICATION";

    public FailureAuthenticationException() {
        super(msg, status, code);
    }

    public FailureAuthenticationException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

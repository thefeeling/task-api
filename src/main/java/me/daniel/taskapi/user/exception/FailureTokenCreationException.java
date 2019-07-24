package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class FailureTokenCreationException extends BaseException {
    private static String msg = "인증 토큰 생성에 실패했습니다.";
    private static HttpStatus status = HttpStatus.UNAUTHORIZED;
    private static String code = "FAILURE_TOKEN_CREATION";

    public FailureTokenCreationException() {
        super(msg, status, code);
    }

    public FailureTokenCreationException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

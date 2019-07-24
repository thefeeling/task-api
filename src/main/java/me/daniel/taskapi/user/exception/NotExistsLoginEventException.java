package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class NotExistsLoginEventException extends BaseException {
    private static String msg = "존재하지 않는 로그인 방식입니다.";
    private static HttpStatus status = HttpStatus.BAD_REQUEST;
    private static String code = "NOT_EXIST_LOGIN_EVENT";

    public NotExistsLoginEventException() {
        super(msg, status, code);
    }

    public NotExistsLoginEventException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

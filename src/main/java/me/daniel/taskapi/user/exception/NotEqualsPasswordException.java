package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class NotEqualsPasswordException extends BaseException {
    private static String msg = "패스워드가 일치하지 않습니다.";
    private static HttpStatus status = HttpStatus.BAD_REQUEST;
    private static String code = "NOT_EQUALS_PASSWORD";

    public NotEqualsPasswordException() {
        super(msg, status, code);
    }

    public NotEqualsPasswordException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

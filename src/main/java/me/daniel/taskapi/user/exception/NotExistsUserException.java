package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class NotExistsUserException extends BaseException {
    public NotExistsUserException() {
        super("존재하지 않은 유저입니다.", HttpStatus.BAD_REQUEST, "NOT_EXISTS_USER");
    }
    public NotExistsUserException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

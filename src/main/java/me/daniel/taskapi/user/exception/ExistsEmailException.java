package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class ExistsEmailException extends BaseException {
    public ExistsEmailException() {
        super("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST, "EXISTS_EMAIL");
    }

    public ExistsEmailException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

package me.daniel.taskapi.user.exception;

import me.daniel.taskapi.global.error.BaseException;
import org.springframework.http.HttpStatus;

public class NotExistsJoinTypeException extends BaseException {
    public NotExistsJoinTypeException() {
        super("존재하지 않는 가입방식입니다.", HttpStatus.BAD_REQUEST, "NOT_EXISTS_JOIN_TYPE");
    }
    public NotExistsJoinTypeException(String msg, HttpStatus status, String code) {
        super(msg, status, code);
    }
}

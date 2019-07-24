package me.daniel.taskapi.global.error;


import org.springframework.http.HttpStatus;


public class BaseException extends RuntimeException {
    private String msg = "invalid domain exception";
    private HttpStatus status = HttpStatus.BAD_REQUEST;
    private String code = "BASE_EXCEPTION";
    private String value = "";
    private ErrorResponse errorResponse = null;


    public final String getMsg() {
        return this.msg;
    }

    public final HttpStatus getStatus() {
        return this.status;
    }

    public final String getCode() {
        return this.code;
    }

    public final String getValue() {
        return this.value;
    }

    public final ErrorResponse getErrorResponse() {
        return this.errorResponse;
    }

    public final void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public BaseException() {
        super();
    }

    public BaseException(String msg, HttpStatus status, String code) {
        super(msg);
        this.msg = msg;
        this.status = status;
        this.code = code;
    }
}

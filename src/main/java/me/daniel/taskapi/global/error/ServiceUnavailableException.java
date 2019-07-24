package me.daniel.taskapi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUnavailableException extends RuntimeException{
    private String msg = "서비스 호출에 실패했습니다.";
    private HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
    private String code = "SERVICE_UNAVAILABLE";

    public ServiceUnavailableException(String msg) {
        super(msg);
        this.msg = msg;
    }
}

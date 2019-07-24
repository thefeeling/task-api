package me.daniel.taskapi.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class UserDto {

    @AllArgsConstructor
    @Getter
    public static class Me {
        private Long id;
        private String email;
        private LocalDateTime loginedAt;
    }

//    @Getter
//    @NoArgsConstructor(access = AccessLevel.PROTECTED)
//    public static class JoinReq {
//        @NotBlank(message = "이메일(`email`)은 빈 값을 허용하지 않으며 필수입니다.")
//        @Email(message = "이메일(`email`) 형식이 올바르지 않습니다.")
//        private String email;
//
//        @NotBlank(message = "패스워드(`password`)는 빈 값을 허용하지 않으며 필수입니다.")
//        @Size(min = 8, max = 15, message = "패스워드(`password`)는 최소 `8글자` 최대 `15자` 입니다.")
//        private String password;
//
//        @Builder
//        public JoinReq(String email, String password) {
//            this.email = email;
//            this.password = password;
//        }
//    }





}

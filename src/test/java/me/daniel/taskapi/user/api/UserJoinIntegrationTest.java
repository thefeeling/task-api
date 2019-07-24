package me.daniel.taskapi.user.api;

import me.daniel.taskapi.IntegrationTest;
import me.daniel.taskapi.user.dto.JoinDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[유저서비스] - 회원가입 통합테스트")
class UserJoinIntegrationTest extends IntegrationTest {
    private ResultActions requestSignUp(JoinDto.Req dto) throws Exception {
        return mvc.perform(post("/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    @Test
    @DisplayName("이메일(`email`)이 없으면 요청에 실패해야 한다.")
    void notExistsEmail() throws Exception {
        // Given
        JoinDto.Req dto = JoinDto.Req.builder().email("").password("asdfzxcvq").build();
        // When
        ResultActions resultActions = requestSignUp(dto);
        // Then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("이메일(`email`)은 빈 값을 허용하지 않으며 필수입니다.")));
    }

    @Test
    @DisplayName("이메일(`email`) 형식이 아니면 요청에 실패해야 한다.")
    void notValidEmail() throws Exception {
        // Given
        JoinDto.Req dto = JoinDto.Req.builder().email("kkss").password("asdfzxcvq").build();
        // When
        ResultActions resultActions = requestSignUp(dto);
        // Then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("이메일(`email`) 형식이 올바르지 않습니다.")));

    }

    @Test
    @DisplayName("패스워드(`password`)가 없으면 요청에 실패해야 한다.")
    void notExistsPassword() throws Exception {
        // Given
        JoinDto.Req dto = JoinDto.Req.builder().email("domain@domain.io").build();
        // When
        ResultActions resultActions = requestSignUp(dto);
        // Then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("패스워드(`password`)는 빈 값을 허용하지 않으며 필수입니다.")));
    }

    @Test
    @DisplayName("패스워드(`password`)가 정해진 길이에 맞지 않으면 요청에 실패해야 한다.")
    void notValidLengthOfPassword() throws Exception {
        // Given
        JoinDto.Req dto = JoinDto.Req.builder().email("domain@domain.io").password("asdf").build();
        // When
        ResultActions resultActions = requestSignUp(dto);
        // Then
        resultActions.andExpect(status().is4xxClientError())
            .andExpect(jsonPath("status").exists())
            .andExpect(jsonPath("status").isNumber())
            .andExpect(jsonPath("status").value(400))
            .andExpect(jsonPath("errors").exists())
            .andExpect(jsonPath("errors").isArray())
            .andExpect(jsonPath("$.errors[0].reason", is("패스워드(`password`)는 최소 `8글자` 최대 `15자` 입니다.")));
    }

    @Test
    @DisplayName("이메일(`email`)과 패스워드(`password`)가 모두 유효한 값이면 회원가입에 성공해야 한다.")
    void success() throws Exception {
        // Given
        JoinDto.Req dto = JoinDto.Req.builder().email("domain@domain.io").password("qwerasdf1234!").build();
        // When
        ResultActions resultActions = requestSignUp(dto);
        // Then
        resultActions.andExpect(status().isNoContent());
    }

}

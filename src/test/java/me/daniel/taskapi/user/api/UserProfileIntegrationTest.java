package me.daniel.taskapi.user.api;

import me.daniel.taskapi.IntegrationTest;
import me.daniel.taskapi.user.dto.LoginDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("[유저서비스] - 프로필 조회 통합테스트")
class UserProfileIntegrationTest extends IntegrationTest {

    private LoginDto.Res loginResDto = null;

    @BeforeEach
    void beforeEach() throws Exception {
        LoginDto.Req dto = LoginDto.Req.builder()
                .email("kschoi@dev.io").password("qwerasdf1234!")
                .build();
        this.loginResDto = this.requestUserJoin(dto);
    }

    @AfterEach
    void AfterEach() {
        this.loginResDto = null;
    }

    @Test
    @DisplayName("인증토큰이 없으면 요청에 실패해야 한다.")
    void empty_token() throws Exception {
        // Given
        // When
        ResultActions resultActions = mvc.perform(
                get("/v1/users/me")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(print());
        // Then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("인증 토큰은 필수입니다.")));
    }

    @Test
    @DisplayName("인증 토큰의 타입이 `Bearer`가 아니면 요청에 실패해야 한다.")
    void invalid_token_type() throws Exception {
        // Given
        String tokenType = "Basic";
        // When
        ResultActions resultActions = mvc.perform(
                get("/v1/users/me")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", tokenType + " " + loginResDto.getAccessToken())
        ).andDo(print());
        // Then
        resultActions.andExpect(status().is4xxClientError())
            .andExpect(jsonPath("status").exists())
            .andExpect(jsonPath("status").isNumber())
            .andExpect(jsonPath("status").value(400))
            .andExpect(jsonPath("errors").exists())
            .andExpect(jsonPath("errors").isArray())
            .andExpect(jsonPath("$.errors[0].reason", is("토큰 타입은 `Bearer` 입니다.")));
    }

    @Test
    @DisplayName("만료된 토큰일 경우 요청에 실패해야 한다.")
    void expired_token() throws Exception {
        // Given
        String expiredJwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrc2Nob2kiLCJpZCI6MSwiYXV0aFR5cGUiOiJMT0NBTCIsImV4cCI6MTU2Mzk2NzcwOCwiaWF0IjoxNTYzOTY3NzA3fQ.tCFutRSn-hcVEANte3FmXBl6OvghHg3ifXGaCgP2t5U";
        // When
        ResultActions resultActions = mvc.perform(
                get("/v1/users/me")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + expiredJwtToken)
        ).andDo(print());
        // Then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("만료된 인증 토큰입니다.")));
    }

    @Test
    @DisplayName("비정상적인 토큰일 경우 요청에 실패해야 한다.")
    void invalid_token_value() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/v1/users/me")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + "asdfasdfasdfasdf")
        ).andDo(print());
        // Then
        resultActions.andExpect(status().is4xxClientError())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("인증에 실패했습니다.")));
    }

    @Test
    @DisplayName("정상적인 토큰일 경우 요청에 성공해야 한다.")
    void success() throws Exception {
        ResultActions resultActions = mvc.perform(
            get("/v1/users/me")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Bearer " + loginResDto.getAccessToken())
        ).andDo(print());
        // Then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("id").isNumber())
            .andExpect(jsonPath("email").exists())
            .andExpect(jsonPath("email").isString())
            .andExpect(jsonPath("loginedAt").exists())
            .andExpect(jsonPath("loginedAt").isString());
    }

}

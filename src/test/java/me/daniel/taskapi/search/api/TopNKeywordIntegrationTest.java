package me.daniel.taskapi.search.api;

import me.daniel.taskapi.IntegrationTest;
import me.daniel.taskapi.user.dto.LoginDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[검색서비스] - 인기 키워드 목록 조회 통합테스트")
class TopNKeywordIntegrationTest extends IntegrationTest {

    private ResultActions requestGetTopNKeywords(String accessToken) throws Exception {
        return mvc.perform(
            get("/v1/search/top-n-keywords")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization", "Bearer " + accessToken)
        ).andDo(print());
    }

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
    @DisplayName("키워드 목록은 10개가 반환되어야 한다.")
    void notExistsEmail() throws Exception {
        // When
        ResultActions resultActions = requestGetTopNKeywords(this.loginResDto.getAccessToken());
        // Then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("updatedAt").exists())
            .andExpect(jsonPath("updatedAt").isString())
            .andExpect(jsonPath("list").exists())
            .andExpect(jsonPath("list").isArray())
            .andExpect(jsonPath("$.list").value(Matchers.hasSize(10)));
    }
}

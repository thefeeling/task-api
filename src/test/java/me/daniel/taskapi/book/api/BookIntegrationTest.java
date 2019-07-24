package me.daniel.taskapi.book.api;

import me.daniel.taskapi.IntegrationTest;
import me.daniel.taskapi.user.dto.LoginDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[도서서비스] - 도서 목록 조회 통합테스트")
class BookIntegrationTest extends IntegrationTest {
    private ResultActions requestGetBooks(LinkedMultiValueMap<String, String> params, String accessToken) throws Exception {
        return mvc.perform(
                get("/v1/books")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .header("Authorization", "Bearer " + accessToken)
                    .params(params)
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
    @DisplayName("검색 키워드가 없으면 요청에 실패해야 한다.")
    void notExistsQuery() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // When
        ResultActions resultActions = requestGetBooks(params, this.loginResDto.getAccessToken());
        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("status").isNumber())
                .andExpect(jsonPath("status").value(400))
                .andExpect(jsonPath("errors").exists())
                .andExpect(jsonPath("errors").isArray())
                .andExpect(jsonPath("$.errors[0].reason", is("검색어(`query`)는 필수 값이며 빈 문자열을 허용하지 않습니다.")));
    }

    @Test
    @DisplayName("페이지 번호가 `0` 이하이면 요청에 실패해야 한다.")
    void under_zero_page() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("query", "oo");
        params.set("page", "-1");
        // When
        ResultActions resultActions = requestGetBooks(params, this.loginResDto.getAccessToken());
        // Then
        resultActions
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("status").exists())
            .andExpect(jsonPath("status").isNumber())
            .andExpect(jsonPath("status").value(400))
            .andExpect(jsonPath("errors").exists())
            .andExpect(jsonPath("errors").isArray())
            .andExpect(jsonPath("$.errors[0].reason", is("페이지 번호의 최소 값은 `0`입니다.")));
    }

    @Test
    @DisplayName("페이지 사이즈가 100 이상이면 요청에 실패해야 한다.")
    void over_page_size() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("query", "oo");
        params.set("size", "101");
        // When
        ResultActions resultActions = requestGetBooks(params, this.loginResDto.getAccessToken());
        // Then
        resultActions
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("status").exists())
            .andExpect(jsonPath("status").isNumber())
            .andExpect(jsonPath("status").value(400))
            .andExpect(jsonPath("errors").exists())
            .andExpect(jsonPath("errors").isArray())
            .andExpect(jsonPath("$.errors[0].reason", is("페이지 사이즈(`limit`)의 최대 값은 `100`입니다.")));
    }

    @Test
    @DisplayName("페이징(page=0, size=10)을 처리할 수 있어야 한다.")
    void pagination() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("query", "JPA");
        params.set("page", "0");
        params.set("size", "10");
        // When
        ResultActions resultActions = requestGetBooks(params, this.loginResDto.getAccessToken());
        // Then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("number").exists())
            .andExpect(jsonPath("number").isNumber())
            .andExpect(jsonPath("number").value(0))
            .andExpect(jsonPath("size").exists())
            .andExpect(jsonPath("size").isNumber())
            .andExpect(jsonPath("size").value(10));
    }

    @Test
    @DisplayName("검색결과에 키워드 내용을 포함해야 한다.")
    void keywordFilter() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("query", "JPA");
        // When
        ResultActions resultActions = requestGetBooks(params, this.loginResDto.getAccessToken());
        // Then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.content[*].title").exists())
            .andExpect(jsonPath("$.content[?(@.title =~ /^.*JPA.*$/)]").value(Matchers.notNullValue()))
            .andExpect(jsonPath("$.content[?(@.title =~ /^.*JPA.*$/)]").isArray());
    }
}

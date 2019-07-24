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
import org.springframework.util.LinkedMultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("[검색서비스] - 회원 검색 키워드 목록 조회 통합테스트")
class UserSearchKeywordIntegrationTest extends IntegrationTest {
    private ResultActions requestGetUserSearchKeywords(
        String accessToken,
        LinkedMultiValueMap<String, String> params
    ) throws Exception {
        return mvc.perform(
            get("/v1/search/user-search-keywords")
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
    @DisplayName("페이징(page=1, size=10)을 할 수 있어야 한다.")
    void testPagination() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("page", "1");
        params.set("size", "10");
        // When
        ResultActions resultActions = requestGetUserSearchKeywords(this.loginResDto.getAccessToken(), params);
        // Then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("size").exists())
            .andExpect(jsonPath("size").isNumber())
            .andExpect(jsonPath("size").value(10))
            .andExpect(jsonPath("number").exists())
            .andExpect(jsonPath("number").isNumber())
            .andExpect(jsonPath("number").value(1))
            .andExpect(jsonPath("$.content").value(Matchers.hasSize(10)));
    }

    @Test
    @DisplayName("카테고리로 필터 할 수 있어야 한다.")
    void testCategoryFilter() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("category", "BOOK");
        // When
        ResultActions resultActions = requestGetUserSearchKeywords(this.loginResDto.getAccessToken(), params);
        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.content").value(Matchers.hasSize(10)))
                .andExpect(jsonPath("$.content[*].category").value(
                    Matchers.everyItem(Matchers.is("BOOK"))
                ));
    }

    @Test
    @DisplayName("조회 결과에 키워드를 포함해야 한다.")
    void testKeywordFilter() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("keyword", "Doors");
        // When
        ResultActions resultActions = requestGetUserSearchKeywords(this.loginResDto.getAccessToken(), params);
        // Then
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.content[?(@.keyword =~ /^.*Doors.*$/)]").value(Matchers.notNullValue()))
            .andExpect(jsonPath("$.content[?(@.keyword =~ /^.*Doors.*$/)]").isArray());
    }

    @Test
    @DisplayName("데이터가 존재하지 않으면 빈 배열을 반환해야 한다.")
    void testEmptyResultKeywordFilter() throws Exception {
        // Given
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("keyword", "😀");
        // When
        ResultActions resultActions = requestGetUserSearchKeywords(this.loginResDto.getAccessToken(), params);
        // Then
        resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content").value(Matchers.hasSize(0)));
    }

}

package me.daniel.taskapi.search.api;


import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.global.auth.AuthHelper;
import me.daniel.taskapi.global.auth.Authorization;
import me.daniel.taskapi.global.auth.TokenPayload;
import me.daniel.taskapi.global.model.search.SearchCategory;
import me.daniel.taskapi.search.application.SearchService;
import me.daniel.taskapi.search.dto.TopKeywordDto;
import me.daniel.taskapi.search.dto.UserSearchKeywordDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;

@RestController
@RequestMapping("/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    /**
     * SearchCategory Enum 바인딩
     * @param dataBinder - WebDataBinder
     */
    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(SearchCategory.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                this.setValue(SearchCategory.valueOf(text));
            }
        });
    }


    /**
     * 인기 키워드 목록
     *   - 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
     *   - 키워드 별로 검색된 횟수도 함께 표기해 주세요.
     * @param req - 요청 DTO, N개에 대한 queryString 처리
     * @return ResponseEntity<TopKeywordDto.Res>
     */
    @Authorization
    @GetMapping("/top-n-keywords")
    public ResponseEntity<TopKeywordDto.Res> topNKeywords(@Valid TopKeywordDto.Req req) {
        return ResponseEntity.ok(
            searchService.getTopNKeyword(req)
        );
    }

    /**
     * 내 검색 히스토리
     *   - 나의 검색 히스토리(키워드, 검색 일시)를 최신 순으로 보여 주세요.
     * @param req - 요청 DTO
     * @param pageable - 페이징 데이터 Object
     * @return ResponseEntity<Page<UserSearchKeywordDto.Res>>
     */
    @Authorization
    @GetMapping("/user-search-keywords")
    public ResponseEntity<Page<UserSearchKeywordDto.Res>> list(
        @Valid UserSearchKeywordDto.Req req,
        @PageableDefault @SortDefault.SortDefaults(value = @SortDefault(sort = "id", direction = Sort.Direction.DESC))
        Pageable pageable
    ) {
        TokenPayload currentTokenPayload = AuthHelper.getCurrentTokenPayload();
        return ResponseEntity.ok(
            searchService.getUserSearchKeywords(currentTokenPayload, req, pageable)
        );
    }



}

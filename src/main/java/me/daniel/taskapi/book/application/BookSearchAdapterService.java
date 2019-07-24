package me.daniel.taskapi.book.application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.daniel.taskapi.book.dao.KakaoSearchDao;
import me.daniel.taskapi.book.dao.NaverSearchDao;
import me.daniel.taskapi.book.dto.BookDto;
import me.daniel.taskapi.book.dto.KakaoBookSearchDto;
import me.daniel.taskapi.book.dto.NaverBookSearchDto;
import me.daniel.taskapi.global.auth.TokenPayload;
import me.daniel.taskapi.global.error.ServiceUnavailableException;
import me.daniel.taskapi.global.event.search.SearchedEvent;
import me.daniel.taskapi.global.event.search.UserSearchedEvent;
import me.daniel.taskapi.global.model.search.Keyword;
import me.daniel.taskapi.global.model.search.SearchCategory;
import me.daniel.taskapi.global.model.user.UserId;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static me.daniel.taskapi.book.dto.KakaoBookSearchDto.SearchReq;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookSearchAdapterService implements ApplicationEventPublisherAware {

    private final KakaoSearchDao kakaoBookSearchDao;
    private final NaverSearchDao naverSearchDao;

    private ApplicationEventPublisher eventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    private void processPublishEvent(TokenPayload payload, BookDto.SearchReq searchDto) {
        UserSearchedEvent e = new UserSearchedEvent(
            UserId.of(payload.getId()),
            new SearchedEvent(SearchCategory.BOOK, Keyword.of(searchDto.getQuery()))
        );
        eventPublisher.publishEvent(e);
    }

    private Page<BookDto.Book> searchNaverBooks(BookDto.SearchReq searchDto) {
        NaverBookSearchDto.SearchRes naverRes = naverSearchDao.search(
            NaverBookSearchDto.SearchReq.of(searchDto)
        );
        if (!naverRes.isSuccess()) {
            throw new ServiceUnavailableException(
                "도서 OPEN-API 호출에 실패했습니다.",
                HttpStatus.SERVICE_UNAVAILABLE,
                "FAILURE_CALL_OPEN_API"
            );
        }
        return naverRes.toPageResult(searchDto.getPage(), searchDto.getSize());
    }

    private Page<BookDto.Book> getBookPage(BookDto.SearchReq searchDto, KakaoBookSearchDto.SearchRes search) {
        if (search.isSuccess()) {
            return search.toPageResult(searchDto.getPage(), searchDto.getSize());
        } else {
            return searchNaverBooks(searchDto);
        }
    }

    public Page<BookDto.Book> doSearch(TokenPayload payload, BookDto.SearchReq searchDto) {
        KakaoBookSearchDto.SearchRes search = kakaoBookSearchDao.search(
            SearchReq.of(searchDto)
        );
        Page<BookDto.Book> page = getBookPage(searchDto, search);
        processPublishEvent(payload, searchDto);
        return page;
    }
}

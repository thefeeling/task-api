package me.daniel.taskapi.search.application;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.daniel.taskapi.global.auth.TokenPayload;
import me.daniel.taskapi.global.model.user.UserId;
import me.daniel.taskapi.search.dao.SearchHistoryPredicateBuilder;
import me.daniel.taskapi.search.dao.SearchHistoryRepository;
import me.daniel.taskapi.search.dao.UserSearchHistoryRepository;
import me.daniel.taskapi.search.dto.TopKeywordDto;
import me.daniel.taskapi.search.dto.UserSearchKeywordDto;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@CacheConfig(cacheNames={"SEARCH"})
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final UserSearchHistoryRepository userSearchHistoryRepository;

    @Cacheable
    public TopKeywordDto.Res getTopNKeyword(TopKeywordDto.Req req) {
        log.info("getTopNKeyword {}", req);
        return TopKeywordDto.Res.of(
            LocalDateTime.now(),
            searchHistoryRepository.findTopNKeyword(req.getLimit())
        );
    }

    public Page<UserSearchKeywordDto.Res> getUserSearchKeywords(
        TokenPayload payload,
        UserSearchKeywordDto.Req req,
        Pageable pageable
    ) {
        log.info("getUserSearchKeywords {} {} {}", payload, req, pageable);
        Predicate predicate = SearchHistoryPredicateBuilder.of()
                .withUser(UserId.of(payload.getId()))
                .withKeyword(req.getKeyword())
                .withCategory(req.getCategory())
                .toPredicate();
        return userSearchHistoryRepository
                .fetchPage(predicate, pageable)
                .map(UserSearchKeywordDto.Res::of);
    }

}

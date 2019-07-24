package me.daniel.taskapi.search.dao;

import com.querydsl.core.types.Projections;
import lombok.extern.slf4j.Slf4j;
import me.daniel.taskapi.search.domain.QSearchHistory;
import me.daniel.taskapi.search.domain.SearchHistory;
import me.daniel.taskapi.search.dto.TopKeywordDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class SearchHistoryRepositoryImpl extends QuerydslRepositorySupport implements SearchHistoryRepositoryCustom {
    public SearchHistoryRepositoryImpl() {
        super(SearchHistory.class);
    }

    @Override
    public List<TopKeywordDto.TopKeywordResult> findTopNKeyword(int limit) {
        return from(QSearchHistory.searchHistory)
                .select(
                    Projections.constructor(
                            TopKeywordDto.TopKeywordResult.class,
                            QSearchHistory.searchHistory.keyword.value,
                            QSearchHistory.searchHistory.keyword.count()
                    )
                )
                .groupBy(QSearchHistory.searchHistory.keyword)
                .orderBy(QSearchHistory.searchHistory.keyword.count().desc())
                .limit(limit > 10 ? 10 : limit)
                .fetch();
    }
}

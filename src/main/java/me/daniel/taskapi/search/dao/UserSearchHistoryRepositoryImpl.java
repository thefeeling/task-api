package me.daniel.taskapi.search.dao;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import me.daniel.taskapi.search.domain.QSearchHistory;
import me.daniel.taskapi.search.domain.QUserSearchHistory;
import me.daniel.taskapi.search.domain.UserSearchHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.Objects;

public class UserSearchHistoryRepositoryImpl extends QuerydslRepositorySupport implements UserSearchHistoryRepositoryCustom {
    public UserSearchHistoryRepositoryImpl() {
        super(UserSearchHistory.class);
    }

    @Override
    public Page fetchPage(Predicate predicate, Pageable pageable) {
        JPQLQuery<UserSearchHistory> query = from(QUserSearchHistory.userSearchHistory)
                .innerJoin(QUserSearchHistory.userSearchHistory.searchHistory, QSearchHistory.searchHistory)
                .fetchJoin()
                .where(predicate);
        JPQLQuery<UserSearchHistory> pagedQuery = Objects
                .requireNonNull(getQuerydsl())
                .applyPagination(pageable, query);
        return PageableExecutionUtils.getPage(pagedQuery.fetch(), pageable, query::fetchCount);
    }
}
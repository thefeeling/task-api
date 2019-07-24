package me.daniel.taskapi.search.dao;

import me.daniel.taskapi.search.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SearchHistoryRepository extends
    JpaRepository<SearchHistory, Long>,
    QuerydslPredicateExecutor<SearchHistory>,
    SearchHistoryRepositoryCustom<SearchHistory> {
}

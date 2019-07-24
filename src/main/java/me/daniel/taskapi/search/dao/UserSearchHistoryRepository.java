package me.daniel.taskapi.search.dao;


import me.daniel.taskapi.search.domain.UserSearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserSearchHistoryRepository extends
    JpaRepository<UserSearchHistory, Long>,
    QuerydslPredicateExecutor<UserSearchHistory>,
    UserSearchHistoryRepositoryCustom<UserSearchHistory> {
}

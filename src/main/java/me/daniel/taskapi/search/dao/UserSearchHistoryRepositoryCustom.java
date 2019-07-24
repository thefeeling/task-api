package me.daniel.taskapi.search.dao;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserSearchHistoryRepositoryCustom<UserSearchHistory> {
    Page<UserSearchHistory> fetchPage(Predicate predicate, Pageable pageable);
}

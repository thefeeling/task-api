package me.daniel.taskapi.search.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import me.daniel.taskapi.global.model.search.SearchCategory;
import me.daniel.taskapi.global.model.user.UserId;
import me.daniel.taskapi.search.domain.QSearchHistory;
import me.daniel.taskapi.search.domain.QUserSearchHistory;
import org.springframework.util.StringUtils;

public class SearchHistoryPredicateBuilder {

    private final BooleanBuilder predicate;

    private SearchHistoryPredicateBuilder() {
        this.predicate = new BooleanBuilder();
    }

    public static SearchHistoryPredicateBuilder of() {
        return new SearchHistoryPredicateBuilder();
    }

    public SearchHistoryPredicateBuilder withUser(UserId userId) {
        if (userId == null || userId.getValue() == 0L) return this;
        predicate.and(
            QUserSearchHistory.userSearchHistory.userId.eq(userId)
        );
        return this;
    }

    public SearchHistoryPredicateBuilder withKeyword(String keyword) {
        if (StringUtils.isEmpty(keyword)) return this;
        String likedKeyword = "%" + keyword + "%";
        predicate.and(
            QSearchHistory.searchHistory.keyword.value.like(likedKeyword.trim())
        );
        return this;
    }

    public SearchHistoryPredicateBuilder withCategory(SearchCategory category) {
        if (category == null) return this;
        predicate.and(QSearchHistory.searchHistory.category.eq(category));
        return this;
    }

    public Predicate toPredicate() {
        return this.predicate.getValue();
    }

}

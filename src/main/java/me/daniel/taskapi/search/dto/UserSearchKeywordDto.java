package me.daniel.taskapi.search.dto;

import lombok.*;
import me.daniel.taskapi.global.model.search.SearchCategory;
import me.daniel.taskapi.search.domain.SearchHistory;
import me.daniel.taskapi.search.domain.UserSearchHistory;

import java.time.LocalDateTime;

public class UserSearchKeywordDto {
    @Getter
    @Setter
    @ToString
    public static class Req {
        private SearchCategory category;
        private String keyword;
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    public static class Res {
        private long id;
        private SearchCategory category;
        private String keyword;
        private LocalDateTime searchedAt;

        public static Res of(UserSearchHistory entity) {
            SearchHistory searchHistory = entity.getSearchHistory();
            return Res.of(
                searchHistory.getId(),
                searchHistory.getCategory(),
                searchHistory.getKeyword().getValue(),
                searchHistory.getSearchedAt()
            );
        }


    }
}

package me.daniel.taskapi.search.domain;

import lombok.*;
import me.daniel.taskapi.global.model.search.Keyword;
import me.daniel.taskapi.global.model.search.SearchCategory;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO: INDEX SearchHistory
 */
@Entity
@Table(name = "search_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "category", "keyword", "searchedAt"})
public class SearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = 0L;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 30, nullable = false)
    private SearchCategory category;

    @Embedded
    @AttributeOverrides(
        value = @AttributeOverride(
            name = "value",
            column = @Column(name = "keyword", nullable = false)
        )
    )
    private Keyword keyword;

    @CreationTimestamp
    @Column(name = "searchedAt", nullable = false, updatable = false)
    private LocalDateTime searchedAt;

    public static SearchHistory create(SearchCategory category, Keyword keyword) {
        SearchHistory history = new SearchHistory();
        history.category = category;
        history.keyword = keyword;
        return history;
    }

}

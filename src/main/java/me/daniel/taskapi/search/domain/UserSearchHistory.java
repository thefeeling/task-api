package me.daniel.taskapi.search.domain;

import lombok.*;
import me.daniel.taskapi.global.model.user.UserId;

import javax.persistence.*;

@Entity
@Table(name = "user_search_histories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(of = {"id"})
public class UserSearchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    @AttributeOverrides(
        value = @AttributeOverride(name = "value", column = @Column(name = "user_id", nullable = false))
    )
    private UserId userId;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity=SearchHistory.class, fetch=FetchType.LAZY)
    @JoinColumn(name="search_history_id")
    private SearchHistory searchHistory;

    public static UserSearchHistory create(UserId userId, SearchHistory searchHistory) {
        UserSearchHistory history = new UserSearchHistory();
        history.userId = userId;
        history.searchHistory = searchHistory;
        return history;
    }


}

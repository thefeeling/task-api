package me.daniel.taskapi.search.event;


import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.global.event.search.SearchedEvent;
import me.daniel.taskapi.global.event.search.UserSearchedEvent;
import me.daniel.taskapi.search.dao.SearchHistoryRepository;
import me.daniel.taskapi.search.dao.UserSearchHistoryRepository;
import me.daniel.taskapi.search.domain.SearchHistory;
import me.daniel.taskapi.search.domain.UserSearchHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchedEventListener {
    private static Logger logger = LoggerFactory.getLogger(SearchedEventListener.class);

    private final SearchHistoryRepository repository;
    private final UserSearchHistoryRepository userSearchHistoryRepository;

    @Async(value = "threadPoolTaskExecutor")
    @EventListener(classes = UserSearchedEvent.class)
    public void handleUserSearchedEvent(UserSearchedEvent e) {
        logger.info("handleUserSearchedEvent {}", e);
        SearchedEvent searchedEvent = e.getSearchedEvent();
        SearchHistory history = SearchHistory.create(
            searchedEvent.getSearchCategory(), searchedEvent.getKeyword()
        );
        UserSearchHistory userHistory = UserSearchHistory.create(e.getUserId(), history);
        userSearchHistoryRepository.save(userHistory);
    }

    /**
     * TODO: 개별 검색 이력 사용 유무 추후에 체크하여 삭제하던지 유지하던지 해야 함.
     * @param e - 검색 이벤트 객체
     */
    @Async(value = "threadPoolTaskExecutor")
    @EventListener(classes = SearchedEvent.class)
    public void handleSearchedEvent(SearchedEvent e) {
        logger.info("handleSearchedEvent {}", e);
        SearchHistory history = SearchHistory.create(e.getSearchCategory(), e.getKeyword());
        repository.save(history);
    }
}

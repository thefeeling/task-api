package me.daniel.taskapi.user.event;


import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.user.dao.UserLoginEventRepository;
import me.daniel.taskapi.user.domain.UserLoginEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class UserLoginedEventListener {
    private final UserLoginEventRepository repository;

    private static Logger logger = LoggerFactory.getLogger(UserLoginedEventListener.class);

    @Async(value = "threadPoolTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION, classes = UserLoginedEvent.class)
    @Transactional(readOnly = false)
    public void handle(UserLoginedEvent e) {
        logger.info("UserLoginedEventListener {}", e);
        repository.save(UserLoginEvent.of(e.getUserId(), e.getAuthType()));
    }
}

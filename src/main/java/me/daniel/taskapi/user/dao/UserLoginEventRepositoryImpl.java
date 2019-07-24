package me.daniel.taskapi.user.dao;

import me.daniel.taskapi.global.model.user.UserId;
import me.daniel.taskapi.user.domain.QUserLoginEvent;
import me.daniel.taskapi.user.domain.UserLoginEvent;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;

public class UserLoginEventRepositoryImpl extends QuerydslRepositorySupport implements UserLoginEventRepositoryCustom {
    public UserLoginEventRepositoryImpl() {
        super(UserLoginEvent.class);
    }

    @Override
    public LocalDateTime findLatestLoginedAtByUserId(UserId userId) {
        return from(QUserLoginEvent.userLoginEvent)
            .select(QUserLoginEvent.userLoginEvent.createdAt)
            .where(QUserLoginEvent.userLoginEvent.userId.eq(userId))
            .orderBy(QUserLoginEvent.userLoginEvent.createdAt.desc())
            .limit(1)
            .fetchOne();
    }
}

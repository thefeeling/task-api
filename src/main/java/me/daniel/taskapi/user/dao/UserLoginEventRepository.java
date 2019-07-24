package me.daniel.taskapi.user.dao;

import me.daniel.taskapi.user.domain.UserLoginEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserLoginEventRepository extends JpaRepository<UserLoginEvent, Long>,
        QuerydslPredicateExecutor<UserLoginEvent>,
        UserLoginEventRepositoryCustom {
}

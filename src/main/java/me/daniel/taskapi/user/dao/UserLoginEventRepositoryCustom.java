package me.daniel.taskapi.user.dao;

import me.daniel.taskapi.global.model.user.UserId;

import java.time.LocalDateTime;

public interface UserLoginEventRepositoryCustom {
    LocalDateTime findLatestLoginedAtByUserId(UserId userId);
}

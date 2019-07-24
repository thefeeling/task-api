package me.daniel.taskapi.global.event.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.daniel.taskapi.global.model.user.UserId;

@Getter
@RequiredArgsConstructor
@ToString
public class UserSearchedEvent {
    private final UserId userId;
    private final SearchedEvent searchedEvent;
}

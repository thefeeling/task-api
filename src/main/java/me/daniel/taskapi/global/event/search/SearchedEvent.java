package me.daniel.taskapi.global.event.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import me.daniel.taskapi.global.model.search.Keyword;
import me.daniel.taskapi.global.model.search.SearchCategory;

@Getter
@RequiredArgsConstructor
@ToString
public class SearchedEvent {
    private final SearchCategory searchCategory;
    private final Keyword keyword;
}

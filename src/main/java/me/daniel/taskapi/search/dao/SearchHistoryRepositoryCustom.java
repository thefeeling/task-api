package me.daniel.taskapi.search.dao;

import me.daniel.taskapi.search.dto.TopKeywordDto;

import java.util.List;

public interface SearchHistoryRepositoryCustom<SearchHistory> {
    List<TopKeywordDto.TopKeywordResult> findTopNKeyword(int limit);
}

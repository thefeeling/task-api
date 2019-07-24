package me.daniel.taskapi.book.dao;

public interface BookSearchDao<IN, OUT> {
     OUT search(IN e);
}

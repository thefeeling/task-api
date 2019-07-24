package me.daniel.taskapi.search.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSearchHistory is a Querydsl query type for SearchHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSearchHistory extends EntityPathBase<SearchHistory> {

    private static final long serialVersionUID = -249326634L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSearchHistory searchHistory = new QSearchHistory("searchHistory");

    public final EnumPath<me.daniel.taskapi.global.model.search.SearchCategory> category = createEnum("category", me.daniel.taskapi.global.model.search.SearchCategory.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final me.daniel.taskapi.global.model.search.QKeyword keyword;

    public final DateTimePath<java.time.LocalDateTime> searchedAt = createDateTime("searchedAt", java.time.LocalDateTime.class);

    public QSearchHistory(String variable) {
        this(SearchHistory.class, forVariable(variable), INITS);
    }

    public QSearchHistory(Path<? extends SearchHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSearchHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSearchHistory(PathMetadata metadata, PathInits inits) {
        this(SearchHistory.class, metadata, inits);
    }

    public QSearchHistory(Class<? extends SearchHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.keyword = inits.isInitialized("keyword") ? new me.daniel.taskapi.global.model.search.QKeyword(forProperty("keyword")) : null;
    }

}


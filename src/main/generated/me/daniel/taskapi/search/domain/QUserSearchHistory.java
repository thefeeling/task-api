package me.daniel.taskapi.search.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSearchHistory is a Querydsl query type for UserSearchHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserSearchHistory extends EntityPathBase<UserSearchHistory> {

    private static final long serialVersionUID = 166233291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSearchHistory userSearchHistory = new QUserSearchHistory("userSearchHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSearchHistory searchHistory;

    public final me.daniel.taskapi.global.model.user.QUserId userId;

    public QUserSearchHistory(String variable) {
        this(UserSearchHistory.class, forVariable(variable), INITS);
    }

    public QUserSearchHistory(Path<? extends UserSearchHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSearchHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSearchHistory(PathMetadata metadata, PathInits inits) {
        this(UserSearchHistory.class, metadata, inits);
    }

    public QUserSearchHistory(Class<? extends UserSearchHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.searchHistory = inits.isInitialized("searchHistory") ? new QSearchHistory(forProperty("searchHistory"), inits.get("searchHistory")) : null;
        this.userId = inits.isInitialized("userId") ? new me.daniel.taskapi.global.model.user.QUserId(forProperty("userId")) : null;
    }

}


package me.daniel.taskapi.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserLoginEvent is a Querydsl query type for UserLoginEvent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserLoginEvent extends EntityPathBase<UserLoginEvent> {

    private static final long serialVersionUID = -311846795L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserLoginEvent userLoginEvent = new QUserLoginEvent("userLoginEvent");

    public final EnumPath<me.daniel.taskapi.global.auth.AuthType> authType = createEnum("authType", me.daniel.taskapi.global.auth.AuthType.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final me.daniel.taskapi.global.model.user.QUserId userId;

    public QUserLoginEvent(String variable) {
        this(UserLoginEvent.class, forVariable(variable), INITS);
    }

    public QUserLoginEvent(Path<? extends UserLoginEvent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserLoginEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserLoginEvent(PathMetadata metadata, PathInits inits) {
        this(UserLoginEvent.class, metadata, inits);
    }

    public QUserLoginEvent(Class<? extends UserLoginEvent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userId = inits.isInitialized("userId") ? new me.daniel.taskapi.global.model.user.QUserId(forProperty("userId")) : null;
    }

}


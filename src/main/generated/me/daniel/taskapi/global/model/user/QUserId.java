package me.daniel.taskapi.global.model.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserId is a Querydsl query type for UserId
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserId extends BeanPath<UserId> {

    private static final long serialVersionUID = -2067277173L;

    public static final QUserId userId = new QUserId("userId");

    public final NumberPath<Long> value = createNumber("value", Long.class);

    public QUserId(String variable) {
        super(UserId.class, forVariable(variable));
    }

    public QUserId(Path<? extends UserId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserId(PathMetadata metadata) {
        super(UserId.class, metadata);
    }

}


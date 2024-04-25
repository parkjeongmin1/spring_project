package com.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBComment is a Querydsl query type for BComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBComment extends EntityPathBase<BComment> {

    private static final long serialVersionUID = -1678141005L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBComment bComment = new QBComment("bComment");

    public final QBoard board;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public QBComment(String variable) {
        this(BComment.class, forVariable(variable), INITS);
    }

    public QBComment(Path<? extends BComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBComment(PathMetadata metadata, PathInits inits) {
        this(BComment.class, metadata, inits);
    }

    public QBComment(Class<? extends BComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}


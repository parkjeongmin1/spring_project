package com.board.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.board.dto.QMainPostDto is a Querydsl Projection type for MainPostDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMainPostDto extends ConstructorExpression<MainPostDto> {

    private static final long serialVersionUID = 1066951618L;

    public QMainPostDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> category, com.querydsl.core.types.Expression<String> content) {
        super(MainPostDto.class, new Class<?>[]{long.class, String.class, String.class, String.class}, id, title, category, content);
    }

}


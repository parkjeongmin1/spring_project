package com.board.repository;

import com.board.dto.MainPostDto;
import com.board.dto.PostSearhDto;
import com.board.dto.QMainPostDto;
import com.board.entity.Post;
import com.board.entity.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class PostRepositoryCustomImpl implements PostRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now(); //현재시간

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1); //1일전
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1); //1주일 전
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1); //1개월 전
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6); //6개월 전
        }
        return QPost.post.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {

        if(StringUtils.equals("title", searchBy)) { //상품명으로 검색 시
            return QPost.post.title.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) { //등록자 검색 시
            return QPost.post.id.like("%" + searchQuery + "%");
        }

        return null;
    }
    @Override
    public Page<Post> getAdminPostPage(PostSearhDto postSearhDto, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(QPost.post)
                .where(regDtsAfter(postSearhDto.getSearchDateType()),
                        searchByLike(postSearhDto.getSearchBy(), postSearhDto.getSearchQuery()))
                .orderBy(QPost.post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QPost.post)
                .where(regDtsAfter(postSearhDto.getSearchDateType()),
                        searchByLike(postSearhDto.getSearchBy(),postSearhDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private  BooleanExpression postTitleLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery)  ? null : QPost.post.title.like("%"+searchQuery+"%");
    }


}

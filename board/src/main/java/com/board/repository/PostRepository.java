package com.board.repository;

import com.board.dto.PostRankDto;
import com.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>,
        QuerydslPredicateExecutor<Post>, PostRepositoryCustom {

}

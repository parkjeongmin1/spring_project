package com.board.repository;

import com.board.entity.BComment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<BComment, Long> {
}

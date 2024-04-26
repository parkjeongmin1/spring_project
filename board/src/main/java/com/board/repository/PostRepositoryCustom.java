package com.board.repository;

import com.board.dto.MainPostDto;
import com.board.dto.PostSearhDto;
import com.board.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<Post> getAdminPostPage(PostSearhDto postSearhDto, Pageable pageable);

}

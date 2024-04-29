package com.board.service;

import com.board.dto.MainPostDto;
import com.board.dto.PostFormDto;
import com.board.dto.PostRankDto;
import com.board.dto.PostSearhDto;
import com.board.entity.Post;
import com.board.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository; //PostRepository 클래스를 가져와서 사용한다.

    public Long savePost(PostFormDto postFormDto)throws Exception{ //postFormDto 의 정보를 담은 객체를 생성한다. throws Exception: 어딘가로 전송한다
        Post post = postFormDto.creatPost(); //postFormDto를 기반으로 Post 객체를 생성한다.
        postRepository.save(post); // Jpa 스프링 데이터 인터페이스에서 save(엔티티) 를 저장한다.
        return post.getId(); // 저장된 post id를 반화나한다. getId(): 저장된 포스트id를 반환하여 다른 곳에서 활용할수 있다.
    }

    //수정 가져오기
    @Transactional(readOnly = true)
    public PostFormDto getPostDtl(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

        PostFormDto postFormDto = PostFormDto.of(post);

        return postFormDto;
    }

    //수정하기
    public Long updatePost(PostFormDto postFormDto) throws Exception{
        Post post = postRepository.findById(postFormDto.getId()).orElseThrow(EntityNotFoundException::new);

        post.updatePost(postFormDto);
        return post.getId();
  }

  @Transactional(readOnly = true)
  public Page<Post> getAdminPostPage(PostSearhDto postSearhDto, Pageable pageable) {
        Page<Post> postPage = postRepository.getAdminPostPage(postSearhDto, pageable);
        return postPage;
  }


  public Page<MainPostDto> getMainPostPage(PostSearhDto postSearhDto, Pageable pageable){
        Page<MainPostDto> mainPostDtoPage = postRepository.getMainPostPage(postSearhDto, pageable);

        return mainPostDtoPage;

  }

}
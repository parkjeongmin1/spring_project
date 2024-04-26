package com.board.dto;

import com.board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostFormDto {
    private Long id; //PK 생성

    private String category;

    @NotNull(message = "제목을 입력해주세요.") //@NotNull: 반드시 값이 존재한다 값이 null 이면 해당 문구를 나타냄
    private String title;                   // @NotBlank: 반드시 값이 존재하고 공백 문자를 제외한 길이가 0보다 커야한다. 그렇지 않으면 해당 문구를 나타냄


    @NotNull(message = "내용을 입력해주세요.")
    private String content;



    private LocalDateTime regTime;

    private static ModelMapper modelMapper = new ModelMapper();

    public Post creatPost(){
        return modelMapper.map(this, Post.class);
    }
    //postFormDto 클래스의 객체를 기반으로 post 객체를 생성한다. modelMapper 를 사용하여 객체 간의 매핑을 수행함
    //postFormDto => post 엔티티로 변환

    public static PostFormDto of(Post post) {
        return modelMapper.map(post, PostFormDto.class);
    }
    //post 객체를 postFromDto 객체로 변환
}

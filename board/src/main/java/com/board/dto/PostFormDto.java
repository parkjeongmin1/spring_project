package com.board.dto;

import com.board.entity.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class PostFormDto {
    private static ModelMapper modelMapper = new ModelMapper();
    private Long id;

    private String category;

    @NotNull(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "내용을 입력해주세요.")
    private String content;

    public Post creatPost(){
        return modelMapper.map(this, Post.class);
    }

    public static PostFormDto of(Post post) {
        return modelMapper.map(post, PostFormDto.class);
    }
}

package com.board.entity;

import com.board.dto.PostFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@Getter
@Setter
@ToString
public class Post extends BaseEntity {


    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String content;

    public void updatePost(PostFormDto postFormDto){
        this.title = postFormDto.getTitle();
        this.category = postFormDto.getCategory();
        this.content = postFormDto.getContent();
    }
}

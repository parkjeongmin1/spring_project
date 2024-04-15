package com.example.project.dto;

import com.sun.jdi.request.StepRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {
    private int postId;
    private String subject;
    private String content;
    private int memberId;
    private String writeDate;
    private String updateDate;
    private int views;
    private String categoryCode;

    private Category category;
    private Member member;
}

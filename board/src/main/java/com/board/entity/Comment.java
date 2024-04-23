package com.board.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "Comment")
@Getter
@Setter
@ToString
public class Comment {

    @Id //PK지정
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키를 자동으로 생성해줌
    private Long id; //Long 타입: PK지정

    private String content;

    private LocalDateTime reg_date;

    @ManyToOne(fetch = FetchType.LAZY) //ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "board_id") //FK 키
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY) //ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "member_id") //FK 키
    private Member member;
}

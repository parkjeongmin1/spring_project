package com.board.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class Board {
    @Id //PK지정
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//기본키를 자동으로 생성해줌
    private Long id; //Long 타입: PK지정

    private String title;

    @Lob//text 가 많을떄에 사용     string 타입은 기본 255값이기 떄문에 더 큰 값을지정하는 옵션
    @Column(columnDefinition = "longtext") //타입을 longtext 로 변경
    private String content;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)//ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "member_id")//FK 키
    private Member member;
}

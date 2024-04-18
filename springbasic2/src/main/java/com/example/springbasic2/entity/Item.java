package com.example.springbasic2.entity;


import com.example.springbasic2.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

//DTO(Data transfer Object): 데이터 전송객체
//Entity 클래스는 DTO 클래스와 다르다.
//Entity 클래스는 DB의 테이블과 대응되는 클래스 => 엔티티 클래스를 통해서 JPA는 테이블 생성 insert, update, delete, select ...


@Entity //현재 클래스를 엔티티 클래스로 사용하겠다 지정
@Table(name = "item") //테이블 이름 지정
@Setter
@Getter
@ToString //Object 객체의 toString 메소드를 오버라이드 하지 않아도 객체 정보를 출력
public class Item {

    @Id //현재 이 속성을 테이블의 PK 로 사용하겠다.
    @Column(name = "item_id") //테이블로 생성될때 컬럼 이름을 지정해준다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AUTO_INCREMENT 지정
    private Long id; ///상품코드, PK

    @Column(nullable = false, length = 50) //not null여부, 컬럼 크기지정
    private String itemNm; //상품명

    @Column(nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob //clob과 같은 큰타입의 문자타입으로 컬럼을 만든다
    @Column(nullable = false, columnDefinition = "longtext")
    private String itemDetail; //상품상세설명

    @Enumerated(EnumType.STRING) //enum 클래스에 저장된 상수를 item_sell_status 컬럼에 문자타입으로 저장
    private ItemSellStatus itemSellStatus; //판매상태 (SELL, SOLD_OUT)

    private LocalDateTime regTime; //상품등록시간

    private LocalDateTime updateTime; //상품수정시간

}

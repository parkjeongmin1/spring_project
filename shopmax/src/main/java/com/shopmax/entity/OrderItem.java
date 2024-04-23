package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@ToString
public class OrderItem extends BaseEntity{

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//기본키를 자동으로 생성해줌
    private Long id;
    private int orderPrice; //주문가격
    private int count; //주문수량

    @ManyToOne(fetch = FetchType.LAZY)//ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "item_id")
    private Item item; //OrderItem 이 Item 을 참조한다.

    @ManyToOne(fetch = FetchType.LAZY)//ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "order_id") //FK 키
    private Order order; //OrderItem 이 Order를 참조한다.

}

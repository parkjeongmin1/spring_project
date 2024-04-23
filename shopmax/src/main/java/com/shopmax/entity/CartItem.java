package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@ToString
public class CartItem extends BaseEntity {

    @Id
    @Column(name = "cart_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키를 자동으로 생성해줌
    private Long id;
    private int count; //장바구니에 담긴 상품수량
    
    @ManyToOne(fetch = FetchType.LAZY) //ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "cart_id") //FK 키
    private Cart cart; //cartItem 은 Cart 를 참조한다.

    @ManyToOne(fetch = FetchType.LAZY) //ERD에서 까치발이 많은쪽에 작성
    @JoinColumn(name = "item_id") //FK 키
    private Item item; //cartItem 은 Item 을 참조한다.
}

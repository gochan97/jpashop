package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlbumForm {
    //상품의 공통 속성
    private Long id; // 상품 수정이 있기 때문에 form에 id값을 넣어준다.

    private String name;
    private int price;
    private int stockQuantity;

    //앨범의 특수한 속성
    private String artist;
    private String etc;
}

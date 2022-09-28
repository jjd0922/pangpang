package com.newper.dto;

import com.newper.entity.ShopProduct;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/** /orders/ 주문 결제 정보 입력 페이지 상품 정보 DTO*/
@Builder
@Setter
@Getter
public class OrdersSpoDTO {

    /** spo{idx}_{idx}*/
    private String val;
    private int cnt;

    private ShopProduct shopProduct;
}

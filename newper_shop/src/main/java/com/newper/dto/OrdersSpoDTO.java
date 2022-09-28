package com.newper.dto;

import com.newper.entity.ShopProduct;
import com.newper.entity.ShopProductAdd;
import com.newper.entity.ShopProductOption;
import com.newper.exception.MsgException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/** /orders/ 주문 결제 정보 입력 페이지 상품 정보 DTO*/
@Builder
@Setter
@Getter
public class OrdersSpoDTO {

    /** spo{idx}_{idx}*/
    private String val;
    private int cnt;
    private int price;

    @Builder.Default
    private List<ShopProductOption> spoList = new ArrayList<>();

    /** add spo*/
    public void addShopProductOption(ShopProductOption spo){
        spoList.add(spo);
    }
}

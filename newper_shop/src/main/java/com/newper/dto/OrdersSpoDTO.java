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
import java.util.Set;

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

    /** 해당 spo idx를 옵션으로 가지고 있는지 체크 (결합상품, 종속옵션)*/
    public boolean hasSpo(Set<Long> spoIdx){
        for (ShopProductOption spo : spoList) {
            if (spoIdx.contains(spo.getSpoIdx().longValue())) {
                return true;
            }
        }
        return false;
    }

    /** 결합된 옵션들 전체 이름*/
    public String getName(){
        String name = "";
        for (ShopProductOption spo : spoList) {
            name += spo.getSpoName()+"\t";
        }
        return name;
    }
}

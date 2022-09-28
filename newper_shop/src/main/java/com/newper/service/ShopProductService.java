package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.ShopProduct;
import com.newper.entity.ShopProductAdd;
import com.newper.entity.ShopProductOption;
import com.newper.exception.MsgException;
import com.newper.repository.ShopProductOptionRepo;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopProductService {

    private final ShopProductRepo shopProductRepo;
    private final ShopProductOptionRepo shopProductOptionRepo;

    /** 주문상세에서 보여질 분양몰 상품 정보 조회. sessionShopIdx != null인 경우 분양몰 일치하는지도 체크 */
    @Transactional(readOnly = true)
    public ShopProduct selectShopProductInfo(long spIdx, Integer sessionShopIdx){

        ShopProduct shopProduct = shopProductRepo.findInfosBySpIdx(spIdx);
        if (shopProduct == null) {
            throw new MsgException("존재하지 않는 상품입니다");
        }

        if( sessionShopIdx != null && shopProduct.getShop().getShopIdx().intValue() != sessionShopIdx ){
            //해당 분양몰에서 판매하는 상품인지 check
            throw new MsgException("존재하지 않는 상품입니다");
        }


        if( !shopProduct.isShow()){
            throw new MsgException("현재 볼 수 없는 상품입니다");
        }

        return shopProduct;
    }
    /** 주문 결제 페이지에서 보여질 상품 정보 조회. key (spo{idx}_{idx}) : value (수량) */
    @Transactional(readOnly = true)
    public void selectOrdersInfo(ParamMap paramMap){

        List<ShopProductOption> spoList = new ArrayList<>();

        Set<Long> spaIncluded = new HashSet<>();
        Set<Long> spaRequired = new HashSet<>();
        for (String key : paramMap.keySet()) {
            if (key.indexOf("spo") == 0) {

                System.out.println(key);
                System.out.println(key.substring(3));

                String[] spos = key.substring(3).split("_");
                for (String spo_str : spos) {
                    System.out.print("spo==  ");
                    System.out.println(spo_str);

                    ShopProductOption spo = shopProductOptionRepo.findSpaBySpoIdx(Long.parseLong(spo_str));
                    spoList.add(spo);

                    System.out.println("test!!!");
                    //현재 spaIdx 기록
                    ShopProductAdd spa = spo.getShopProductAdd();
                    spaIncluded.add(spa.getSpaIdx());
                    //필수 상품 체크해야하는 spa_idx 추가. 아래에서 반복문으로 필수 상품 들어왔는지 확인
                    List<ShopProductAdd> shopProductAddList = spa.getShopProduct().getShopProductAddList();
                    for (ShopProductAdd shopProductAdd : shopProductAddList) {
                        if(shopProductAdd.isSpaRequired()){
                            spaRequired.add(shopProductAdd.getSpaIdx());
                        }
                    }

                }

                System.out.println(paramMap.get(key));
            }
        }

        //필수 spa_idx 포함 되어 있는지 확인
        System.out.println("required!!");
        for (Long spaIdx : spaRequired) {
            System.out.println(spaIdx);

        }

        //상품 재고, 필수상품 포함되어있는지 체크 필요
    }
}

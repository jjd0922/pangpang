package com.newper.service;

import com.newper.entity.ShopProduct;
import com.newper.entity.ShopProductAdd;
import com.newper.entity.ShopProductOption;
import com.newper.exception.MsgException;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopProductService {

    private final ShopProductRepo shopProductRepo;

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
}

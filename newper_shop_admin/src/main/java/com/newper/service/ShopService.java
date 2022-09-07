package com.newper.service;


import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepo shopRepo;

    /**분양몰 추가*/
    @Transactional
    public void shopSave(ParamMap paramMap){
        Shop shop = paramMap.mapParam(Shop.class);
        shop.setShopMileage(0F);
        shop.setShopBasket("Y");

//        shopDesign
//        shop.setShopDesign();

        shopRepo.save(shop);
    }
    /** 분양몰 수정*/
    @Transactional
    public void shopUpdate(ParamMap paramMap){
        Shop shop = shopRepo.findById(paramMap.getInt("shopIdx")).orElseThrow(() -> new MsgException("존재하지 않는 분양몰입니다."));

        paramMap.printEntrySet();
        Shop shopParam = paramMap.mapParam(Shop.class);

        shop.setShopName(shopParam.getShopName());
        shop.setShopState(shopParam.getShopState());
        shop.setShopType(shopParam.getShopType());

    }


    /** 상태 변경*/
    @Transactional(noRollbackFor = MsgException.class)
    public void changeShopState(Integer shopIdx) {
        Shop shop = shopRepo.findById(shopIdx).orElseThrow(() -> new MsgException("존재하지 않는 분양몰입니다."));
        List<Integer> deleteShopIdxs = new ArrayList<>();

        // 삭제 조건 추가
        if(shop.getPg() != null && shop.getPg().getPgState().equals("N")){
           deleteShopIdxs.add(shopIdx);
        }

        shopRepo.deleteAllByIdInBatch(deleteShopIdxs);
    }

    /** 분양몰 디자인 update*/
    public void shopDesignUpdate(Integer shopIdx, ParamMap paramMap) {
        Shop shop = shopRepo.findById(shopIdx).orElseThrow(() -> new MsgException("존재하지 않는 분양몰입니다."));



    }
}



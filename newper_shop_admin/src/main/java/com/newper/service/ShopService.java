package com.newper.service;


import com.newper.constant.FbType;
import com.newper.constant.etc.HoType;
import com.newper.constant.etc.ShopDesign;
import com.newper.dto.ParamMap;
import com.newper.entity.FloatingBar;
import com.newper.entity.HeaderOrder;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.FloatingBarRepo;
import com.newper.repository.HeaderOrderRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepo shopRepo;
    private final HeaderOrderRepo headerOrderRepo;
    private final FloatingBarRepo floatingBarRepo;

    /**분양몰 추가*/
    @Transactional
    public void shopSave(ParamMap paramMap){
        Shop shop = paramMap.mapParam(Shop.class);
        shop.setShopMileage(0F);
        shop.setShopBasket("Y");

        Map<String,Object> designMap = new HashMap<>();
        for(ShopDesign shopDesign : ShopDesign.values()){
            designMap.put(shopDesign.name(), shopDesign.getDefaultValue());
        }

        shop.setShopDesign(designMap);
        // shop 먼저 생성
        shopRepo.saveAndFlush(shop);

        //headerOrder insert
        for(int i = 1; i <= 3; i++){
            for(int j = 1; j <=3; j++){
                HeaderOrder headerOrder = HeaderOrder.builder()
                        .shop(shop)
                        .hoType(HoType.NONE)
                        .hoRow(i)
                        .hoCol(j)
                        .build();
                headerOrderRepo.saveAndFlush(headerOrder);
            }
        }
        for(int i=0;i<FbType.values().length;i++){
            if(FbType.values()[i].name().equals("ETC")){
                continue;
            }
            String defaultUrl = "";
            if(FbType.values()[i].getDefaultUrl() != null){
                defaultUrl = FbType.values()[i].getDefaultUrl();
            }
            FloatingBar fb = FloatingBar.builder()
                    .fbName(FbType.values()[i].getOption())
                    .fbDisplay((i + 1)*-1)
                    .fbType(FbType.values()[i])
                    .fbUrl(defaultUrl)
                    .build();
            shop.addFloatingBar(fb);
        }

    }
    /** 분양몰 수정*/
    @Transactional
    public void shopUpdate(ParamMap paramMap){
        Shop shop = shopRepo.findById(paramMap.getInt("shopIdx")).orElseThrow(() -> new MsgException("존재하지 않는 분양몰입니다."));

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

}



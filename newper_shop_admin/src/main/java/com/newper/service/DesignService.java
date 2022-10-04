package com.newper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.constant.etc.HoType;
import com.newper.constant.etc.ShopDesign;
import com.newper.dto.ParamMap;
import com.newper.entity.HeaderOrder;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.HeaderOrderRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DesignService {

    private final ShopRepo shopRepo;
    private final HeaderOrderRepo headerOrderRepo;

    /** 디자인 update*/
    @Transactional
    public void shopDesignUpdate(ParamMap paramMap) {
        Shop shop = shopRepo.findById(paramMap.getInt("shopIdx")).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));

        Map<String,Object> designMap = shop.getShopDesign();
        for (ShopDesign shopDesign : ShopDesign.values()) {
            designMap.put(shopDesign.name(),paramMap.get(shopDesign.name()));
        }
    }

    /** 헤더 update*/
    @Transactional
    public void shopHeaderUpdate(ParamMap paramMap) {
        Shop shop = shopRepo.getReferenceById(paramMap.getInt("shopIdx"));
        Map<String,Object> designMap = new HashMap<>();
        for (ShopDesign shopDesign : ShopDesign.values()) {
            if(paramMap.containsKey(shopDesign.name())){
                designMap.put(shopDesign.name(),paramMap.get(shopDesign.name()));
            }
        }
        shop.getShopDesign().putAll(designMap);
        shop.setShopHdLoginGroup(paramMap.getList("shopHdLoginGroup"));

        for(int i=1;i<=3;i++){
            for(int k=1;k<=3;k++){
                HeaderOrder updateHo = headerOrderRepo.findByShopAndHoRowAndHoCol(shop, i,k).orElseGet(()-> {
                    return HeaderOrder.builder()
                            .shop(shop)
                            .build();
                });

                updateHo.setHoRow(i);
                updateHo.setHoCol(k);

                String hoType_value=paramMap.getString("headerOrder" + i + "_" + k);
                HoType hoType=HoType.없음;

                if(StringUtils.hasText(hoType_value)){
                    hoType = HoType.valueOf(hoType_value);
                }
                updateHo.setHoType(hoType);

                headerOrderRepo.saveAndFlush(updateHo);
            }
        }
    }
}

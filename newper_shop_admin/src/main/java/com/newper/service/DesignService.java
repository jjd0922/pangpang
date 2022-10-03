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
import com.newper.repository.HeaderOrderRepository;
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
    private final HeaderOrderRepository headerOrderRepository;

    /** 디자인 update*/
    @Transactional
    public void shopDesignUpdate(ParamMap paramMap) {
        Shop shop = shopRepo.findById(paramMap.getInt("shopIdx")).orElseThrow(()-> new MsgException("존재하지 않는 분양몰입니다."));

        try {
            String [] paramKeys = paramMap.keySet().toArray(new String[paramMap.keySet().size()]);
            String key;
            String desingKey;
            Map<String,Object> designMap = new HashMap<>();
            for(int i=0; i< paramKeys.length;i++){
                key = paramKeys[i];
                for(int j=0; j< ShopDesign.values().length; j++){
                    desingKey = String.valueOf(ShopDesign.values()[j]);
                    if(key.equals(desingKey)){
                        designMap.put(key,paramMap.get(key));
                    }
                }
            }

            ObjectMapper om =new ObjectMapper();
            om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            String design = om.writeValueAsString(designMap);
            shop.setShopDesign(design);
        } catch (JsonProcessingException e) {
            System.out.println(e);
            throw new MsgException("잠시 후 다시 시도해주세요.");
        }
    }

    /** 헤더 update*/
    @Transactional
    public void shopHeaderUpdate(ParamMap paramMap) {
        Shop shop = shopRepo.getReferenceById(paramMap.getInt("shopIdx"));

        for(int i=1;i<=3;i++){
            for(int k=1;k<=3;k++){
                HeaderOrder updateHo = headerOrderRepository.findByShopAndHoRowAndHoCol(shop, i,k).orElseGet(()-> {
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
                    if(hoType == HoType.헤더형배너){
                        Long bgIdx = paramMap.getLong("bgIdx" + i + "_" + k, true);
                        if(bgIdx == null){
                            //헤더형 배너로 타입 변경 후 모달로 배너그룹 선택하지 않았을 때
                            //기존 헤더값 유지 인경우도 bgIdx == null이므로 db에 type으로 체크
                            if (updateHo.getHoType() != HoType.헤더형배너) {
                                hoType=HoType.없음;
                            }
                        }
                    }
                }
                updateHo.setHoType(hoType);

                headerOrderRepository.saveAndFlush(updateHo);
            }
        }
    }
}

package com.newper.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.constant.etc.ShopDesign;
import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DesignService {

    private final ShopRepo shopRepo;

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
}

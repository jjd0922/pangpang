package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopProductService {

    /** shop_product save*/
    @Transactional
    public int shopProductSave(ParamMap paramMap){

        return 0;
    }


}

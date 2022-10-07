package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.entity.ShopProduct;
import com.newper.exception.MsgException;
import com.newper.repository.ShopProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ShopProductService {

    private final ShopProductRepo shopProductRepo;

    /** shop_product save*/
    @Transactional
    public int shopProductSave(ParamMap paramMap, MultipartFile SP_THUMB_FILE1, MultipartFile SP_THUMB_FILE2, MultipartFile SP_THUMB_FILE3){
        System.out.println(paramMap.getMap());
        ShopProduct shopProduct = paramMap.mapParam(ShopProduct.class);

        shopProductRepo.save(shopProduct);
        return 0;
    }


}

package com.newper.service;

import com.newper.entity.Shop;
import com.newper.mapper.CategoryMapper;
import com.newper.repository.ShopCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final ShopCategoryRepo shopCategoryRepo;

    public Map<String,Object> allCategoryList(Shop shop){
        Map<String,Object> resultMap = new HashMap<>();

        categoryMapper.selectShopCategoryByShopIdx(shop.getShopIdx());
//        categoryMapper.selectAllCategoryByShopProduct();

        return resultMap;
    }
}

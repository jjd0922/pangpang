package com.newper.service;

import com.newper.entity.Shop;
import com.newper.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class CategoryService {
// 참고용 service 작업완료 후 제거예정
    private final CategoryMapper categoryMapper;

    public List<Map<String,Object>> selectAllCategoryList(Shop shop){
        System.out.println("in~~~~~~~~~~~~~~~~~~~~~~");
        //전시대분류 전체 LIST
        List<Map<String, Object>> scateList = categoryMapper.selectAllShopCategory();

        //중분류 전체 조회
        // key : 전시 idx / value : 중분류
        List<Map<String, Object>> middleCateList = categoryMapper.selectAllMiddleCategory();
        Map<Object, List<Map<String, Object>>> csc_scate_idx = middleCateList.stream().collect(Collectors.groupingBy(map -> map.get("CSC_SCATE_IDX")));
        //소분류
        // key : 중분류 idx / value : 소분류
        List<Map<String, Object>> smallCateList = categoryMapper.selectAllSmallCategory();
        Map<Object, List<Map<String, Object>>> middle_cate_idx = smallCateList.stream().collect(Collectors.groupingBy(map -> map.get("CATE_PARENT_IDX")));

        System.out.println(csc_scate_idx.entrySet());
        System.out.println(middle_cate_idx.entrySet());
        System.out.println("out~~~~~~~~~~~~~~~~~~~~~~~~");


//        List<Map<String,Object>> shopCategoryList = categoryMapper.selectShopCategoryByShopIdx(shop.getShopIdx());
//        for(Map<String,Object> shopCategory : shopCategoryList){
//            Map<String,Object> map = new HashMap<>();
//            map.put("scateIdx", shopCategory.get("SCATE_IDX"));
//            map.put("shopIdx", shop.getShopIdx());
//            List<Map<String,Object>> categoryList = categoryMapper.selectAllCategoryByShopProduct(map);
//            for(Map<String,Object> category : categoryList){
//
//            }
//            shopCategory.put("categoryList", categoryList);
//        }

        return scateList;
    }
}

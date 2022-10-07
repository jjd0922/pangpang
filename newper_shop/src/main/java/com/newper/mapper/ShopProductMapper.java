package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopProductMapper {

    /** ShopProductOption 조회 */
    List<Map<String, Object>> selectShopProductOptionList(long sp_idx);


    List<Map<String,Object>> selectscateMainSectionProductList(Map<String,Object> map);
    /** 카테고리 별 상품 조회*/
    List<Map<String, Object>> selectCategoryProductList(Map<String, Object> map);
    int countCategoryProductList(Map<String, Object> map);
}

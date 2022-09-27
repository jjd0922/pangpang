package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    /** 분양몰이 사용중인 상품의 카테고리 조회*/
    List<Map<String, Object>> selectAllCategoryByShopProduct(Map<String,Object> map);

    /** sp_idx 로 전시 분류 scate_idx, scate_name 조회 */
    Map<String, Object> selectShopCategoryBySp(long sp_idx);
}

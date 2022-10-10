package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    /** 전시대분류 전체 조회*/
    List<Map<String, Object>> selectAllShopCategory();
    /** 중분류 전제 조회*/
    List<Map<String, Object>> selectAllMiddleCategory();
    /** 소분류 전체 조회*/
    List<Map<String, Object>> selectAllSmallCategory();
    /** 분양몰에서 사용중인 상품의 소분류 조회*/
    List<Map<String, Object>> selectAllCategoryByProduct(Integer shopIdx);
    /** 분양몰이 사용중인 상품의 카테고리 조회*/
    List<Map<String, Object>> selectAllCategoryByShopProduct(Map<String,Object> map);

    /** sp_idx 로 카테고리 조회 */
    Map<String, Object> selectShopCategoryBySp(long sp_idx);
    /** 카테고리 페이지 내 카테고리명 호출*/
    Map<String, Object> selectCategoryMenuName(Map<String, Object> map);
}

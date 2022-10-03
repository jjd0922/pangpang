package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainSectionMapper {
    /**mainsection dataTable*/
    List<Map<String, Object>> selectMainSectionDatatable(Map<String, Object> map);
    Map<String,Object> countMainSectionDatatable(Map<String, Object> map);

    /** mainsection 상품일 경우 상세조회*/
    List<Map<String,Object>> selectMainSectionShopProductByMsIdx(@Param("msIdx") Long msIdx);

    /** mainsection 상품 등록*/
    void insertMainSectionSp(Map<String, Object> map);
    /** mainsection 상품 삭제*/
    void deleteMainSectionSp(Map<String,Object> msIdx);
    /** mainsection 상품 업데이트*/
    void updateMainSectionSp(Map<String, Object> map);
    /** mainsection msType Both 일 경우 mainsectionSp msspOrder 기준 조회*/
    List<Map<String,Object>> selectMainSectionBannerShopProductByMsIdx(Map<String,Object> map);

    /** mainsection msJson*/
    Map<String, Object> selectMainSectionMsJson(@Param("msIdx") Long msIdx);
    /** mainsection msType category 일 경우 상품 카테고리 조회*/
    List<Map<String, Object>> selectMainSectionShopProductCategoryByMsIdx(@Param("msIdx") Long msIdx, @Param("scateIdx") String scateIdx);
    /** mainsection msType category 일 경우 상품 카테고리 count */
    Map<String, Object> selectMainSectionSpCategoryCount(Map<String, Object> map);
}

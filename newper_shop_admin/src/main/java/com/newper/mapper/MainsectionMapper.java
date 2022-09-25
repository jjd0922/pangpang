package com.newper.mapper;

import com.newper.entity.MainSectionSp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainsectionMapper {
    /**mainsection dataTable*/
    List<Map<String, Object>> selectMainSectionDatatable(Map<String, Object> map);
    Map<String,Object> countMainSectionDatatable(Map<String, Object> map);

    /** mainsection 상품일 경우 상세조회*/
    List<Map<String,Object>> selectMainSectionShopProductByMsIdx(Long msIdx);

    /** mainsection 상품 등록*/
    void insertMainSectionSp(Map<String, Object> map);
    /** mainsection 상품 삭제*/
    void deleteMainSectionSp(Map<String,Object> msIdx);
    /** mainsection 상품 업데이트*/
    void updateMainSectionSp(Map<String, Object> map);
}

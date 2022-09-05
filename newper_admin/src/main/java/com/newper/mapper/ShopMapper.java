package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopMapper {

    /**분양몰 데이터테이블 조회*/
    List<Map<String, Object>> selectShopDatatable(Map<String, Object> map);
    long countShopDatatable(Map<String, Object> map);
}

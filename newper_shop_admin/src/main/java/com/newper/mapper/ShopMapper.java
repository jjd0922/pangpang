package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopMapper {

    /**shop dataTable*/
    List<Map<String, Object>> selectShopDatatable(Map<String, Object> map);
    Integer countShopDatatable(Map<String, Object> map);

    /** shopDesign Json 조회*/
    Map<String, Object> selectShopDesignJson(@Param("shopIdx") Integer shopIdx);
}

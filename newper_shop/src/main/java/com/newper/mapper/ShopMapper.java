package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface ShopMapper {

    /** shopDesign Json 조회*/
    Map<String, Object> selectShopDesignJson(@Param("shopIdx") Integer shopIdx);
}

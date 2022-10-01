package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalculateMapper {
    List<Map<String, Object>> selectProductPurchaseDatatable(Map<String, Object> map);

    long countProductPurchaseDatatable(Map<String, Object> map);

    List<Map<String, Object>> selectCalculateDatatable(Map<String, Object> param);

    Long countCalculateDatatable(Map<String, Object> param);



}

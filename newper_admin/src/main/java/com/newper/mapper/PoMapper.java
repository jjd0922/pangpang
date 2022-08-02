package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PoMapper {
    /** 견적서 조회 쿼리문 */
    List<Map<String, Object>> selectEstimateDataTable(Map<String, Object> param);

    /** 견적서 갯수 조회 쿼리문 */
    long countEstimateDataTable(Map<String, Object> param);
}
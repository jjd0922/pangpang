package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PoMapper {
    List<Map<String, Object>> selectEstimateDataTable(Map<String, Object> param);

    long countEstimateDataTable(Map<String, Object> param);


    /** 나중에 삭제할 쿼리문들 **/
    List<Map<String, Object>> selectCategory(Map<String, Object> param);
}

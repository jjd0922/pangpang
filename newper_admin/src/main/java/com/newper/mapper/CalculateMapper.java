package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalculateMapper {

    List<Map<String, Object>> selectCalculateDatatable(Map<String, Object> param);

    Integer countCalculateDatatable(Map<String, Object> param);


}

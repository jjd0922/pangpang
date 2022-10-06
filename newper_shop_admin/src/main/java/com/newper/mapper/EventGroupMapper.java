package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EventGroupMapper {

    List<Map<String, Object>> selectEventGroupDatatable(Map<String, Object> map);

    long countEventGroupDatatable(Map<String, Object> map);

    List<Map<String, Object>> eventCategoryProductListByEgIdx(@Param("egIdx") Long egIdx);

    List<Map<String,Object>> eventCategoryProductCountListByEgIdx(@Param("egIdx") Long egIdx);
}

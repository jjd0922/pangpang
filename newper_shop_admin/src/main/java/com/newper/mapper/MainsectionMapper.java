package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainsectionMapper {
    /**mainsection dataTable*/
    List<Map<String, Object>> selectMainSectionDatatable(Map<String, Object> map);
    Map<String,Object> countMainSectionDatatable(Map<String, Object> map);
}

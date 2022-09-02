package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CheckMapper {

    /** 입고검수그룹 조회*/
    List<Map<String, Object>> selectCheckGroupDatatable(Map<String, Object> map);
    /** 입고검수그룹 조회*/
    int countCheckGroupDatatable(Map<String, Object> map);

}

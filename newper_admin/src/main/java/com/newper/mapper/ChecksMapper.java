package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChecksMapper {

    /** 입고검수그룹 조회*/
    List<Map<String, Object>> selectCheckGroupDatatable(Map<String, Object> map);
    /** 입고검수그룹 조회*/
    long countCheckGroupDatatable(Map<String, Object> map);

    /** 영업검수 조회*/
    List<Map<String, Object>> selectChecksDatatable(Map<String, Object> map);
    /** 영업검수 조회*/
    long countChecksDatatable(Map<String, Object> map);

}

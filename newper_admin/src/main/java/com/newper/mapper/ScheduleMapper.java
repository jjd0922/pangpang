package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleMapper {

    /**영업활동관리 데이터테이블 조회*/
    List<Map<String, Object>> selectScheduleDatatable(Map<String, Object> map);
    long countScheduleDatatable(Map<String, Object> map);

}

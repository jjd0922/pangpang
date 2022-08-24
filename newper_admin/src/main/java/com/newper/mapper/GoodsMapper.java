package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {

    List<Map<String, Object>> selectWareHouseDataTable(Map<String, Object> map);

    Integer countWareHouseDataTable(Map<String, Object> map);

    /** 공정보드 datatable*/
    List<Map<String, Object>> selectProcessBoardDatatable(Map<String, Object> map);
    /** 공정보드 datatable*/
    long countProcessBoardDatatable(Map<String, Object> map);


}

package com.newper.mapper;

import com.newper.constant.WhState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WarehouseMapper {

    /**창고 데이터테이블 조회*/
    List<Map<String,Object>> selectWarehouseDatatable(Map<String, Object> map);
    long countWarehouseDatatable(Map<String, Object> map);

    /**창고 상태 일괄변경*/
    void changeAllWhState(@Param("whIdxList") String[] whIdxList,
                          @Param("whState") WhState whState);

    /**로케이션 데이터테이블 조회*/
    List<Map<String,Object>> selectLocationDatatable(Map<String, Object> map);
    long countLocationDatatable(Map<String, Object> map);
}

package com.newper.mapper;

import com.newper.constant.LocType;
import com.newper.entity.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LocationMapper {

    /**로케이션 데이터테이블 조회*/
    List<Map<String,Object>> selectLocationDatatable(Map<String, Object> map);
    long countLocationDatatable(Map<String, Object> map);

    /**로케이션 구분 일괄변경*/
    void changeAllLocType(@Param("locIdxs") String[] locIdxs,
                          @Param("locType") LocType locType);
    
    /**로케이션 엑셀 업로드*/
    void insertLocationByExcel(List<Location> locations);

    /**로케이션별 재고현황 데이터테이블 조회 */
    List<Map<String, Object>> selectStockInLocationDatatable(Map<String, Object> map);
    long countStockInLocationDatatable(Map<String, Object> map);

    /** 로케이션에 있는 자산들 조회 */
    List<Map<String,Object>> selectGoodsByLocation(Map<String, Object> map);
}

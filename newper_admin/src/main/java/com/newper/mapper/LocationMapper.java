package com.newper.mapper;

import com.newper.constant.LocType;
import com.newper.constant.WhState;
import com.newper.entity.Location;
import com.newper.entity.SpecList;
import org.apache.ibatis.annotations.MapKey;
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
    /*List<Map<String, Object>> selectStockInLocationDatatable(Map<String, Object> map);*/
    long countStockInLocationDatatable(Map<String, Object> map);

    /** 로케이션에 있는 자산들 조회 */
    List<Map<String,Object>> selectGoodsByLocation(Map<String, Object> map);


/*    void insertGoodsByLocation(@Param("gIdxs") String[] gIdxs);*/

    List<Map<String, Object>> selectListGoodsByLocation(@Param("gIdxs") String[] gIdxs);

    List<Map<String, Object>> selectStockInLocationDatatable(Map<String, Object> map);

    List<Map<String, Object>> selectStockInLocationDatatable2(Map<String, Object> map);


    /** 임시 테이블에 insert */
    void insertLocationMoveGoods(@Param("lm_idx") long lm_idx, @Param("g_idxs") long[] g_idxs);


    /**창고이동 데이터테이블 조회*/
    List<Map<String,Object>> selectStockMoveDatatable(Map<String, Object> map);
    Integer countStockMoveDatatable(Map<String, Object> map);

    List<Map<String, Object>> selectLocationMoveGoods(long lmIdx);



}

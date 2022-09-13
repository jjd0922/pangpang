package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /** 임시 테이블에 insert */
    void insertGoodsTemp(@Param("ggt_idx") String ggt_idx, @Param("g_idxs") String[] g_idxs);

    /** 자산 임시 테이블 조회*/
    List<Map<String, Object>> selectGoodsTempDatatable(Map<String, Object> map);
    /** 자산 임시 테이블 조회*/
    Long countGoodsTempDatatable(Map<String, Object> map);

    /** 입고검수 임시 테이블 제거 **/
    void deleteGoodsGroupTempByGIdxAndGgtIdx(Map<String, Object> map);

    Map<String, Object> selectGoodsByG_IDX(Long aLong);

    /** 입고검수 임시 테이블 삭제 **/
    void deleteGoodsGroupTempByGGT_IDX(@Param("ggtIdx") long ggt_idx);
}

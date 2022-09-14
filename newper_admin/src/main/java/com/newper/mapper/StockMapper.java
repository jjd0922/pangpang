package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {

    List<Map<String,Object>> selectStockDataTable(Map<String,Object> param);

    Integer countStockDataTable(Map<String,Object> param);


    /** 픽킹관리 전체 조회*/
    List<Map<String, Object>> selectStockDatatableByParent();

    /**재고코드로 조회 */
    List<Map<String, Object>> selectStockDatatableByChildren(Integer gs_p_idx);

}

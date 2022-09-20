package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockMapper {

    /** 재고상품 조회 */
    List<Map<String,Object>> selectStockDataTable(Map<String,Object> param);

    /** 재고상품 카운트 */
    Integer countStockDataTable(Map<String,Object> param);


    /** 픽킹관리 전체 조회*/
    List<Map<String, Object>> selectStockDatatableByParent();

    /**재고코드로 조회 */
    List<Map<String, Object>> selectStockDatatableByChildren(Map<String,Object> param);

    /** 재고상품중 소모품만 조회 */
    List selectStockParts(Map<String, Object> map);

    /** 재고상품중 소모품만 카운트 */
    long countStockParts(Map<String, Object> map);
}

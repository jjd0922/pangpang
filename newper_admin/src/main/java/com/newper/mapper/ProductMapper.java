package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    /** 상품 데이터 테이블 조회 */
    List<Map<String, Object>> selectProductDataTable(Map<String,Object> map);
    long countProductDataTable(Map<String,Object> map);

    /**마지막 상품코드 조회*/
//    String selectProductByListPcode();

    /**재고상품 데이터테이블*/
    List<Map<String, Object>> selectGoodsStockDataTable(Map<String, Object> map);
    Integer countGoodsStockDataTable(Map<String, Object> map);

    /**마지막 재고상품 코드 조회*/
    String selectGoodsStockByListGsCode();
}


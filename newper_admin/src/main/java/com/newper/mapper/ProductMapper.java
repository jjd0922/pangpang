package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    /** 상품 데이터 테이블 조회 */
    List<Map<String, Object>> selectProductDataTalbe(Map<String,Object> map);

    long countProductDataTable(Map<String,Object> map);
}

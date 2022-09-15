package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {

    /**통합주문관리 dataTable*/
    List<Map<String, Object>> selectOrderDatatable(Map<String, Object> map);
    Integer countOrderDatatable(Map<String, Object> map);

    /**통합주문관리 상세*/
    Map<String, Object> selectOrderDetail(int O_IDX);

    /**통합주문관리 주문상품*/
    List<Map<String, Object>> selectGoodsStockDetailByOIdx(Map<String, Object> map);


    /**SHOP주문관리 데이터테이블*/
    List<Map<String, Object>> selectOrderShopDatatable(Map<String, Object> map);

    Integer countOrderShopDatatable(Map<String, Object> map);

    /** 송장 등록 */
    void insertInvoice(Map<String, Object> map);

}

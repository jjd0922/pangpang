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

    /**주문코드,재고코드,자산코드로 주문구성상품 조회*/
    Long selectOrderGsDetailByOCodeAndGsCode(Map<String, Object> map);

    /**O_IDX, P_DEL_TYPE으로 order_gs 조회*/
    List<Map<String, Object>> selectGoodsGsDetailByOIdxAndPType(Map<String, Object> map);

    /**출고전(입금대기,주문완료,상품준비중) 상태의 주문*/
    List<Map<String, Object>> selectOrdersListByBeforeRelease();

    /**자산바코드 주문IDX로 주문상품 조회*/
    Map<String, Object> selectOrderGsDetailByOIdxAndGBarcode(Map<String, Object> map);

    /**상품배송타입, 주문IDX로 주문상품 출고일 조회*/
    List<Map<String, Object>> selectOrdersReleaseDateByOIdxAndPDelType(Map<String, Object> map);

    /** 교환/반품/AS 상태인 자산 조회 */
    List<Map<String, Object>> selectServiceGoodsDatatable(Map<String, Object> map);

    /** 교환/반품/AS 상태인 자산 카운트 */
    long countServiceGoodsDatatable(Map<String, Object> map);
}

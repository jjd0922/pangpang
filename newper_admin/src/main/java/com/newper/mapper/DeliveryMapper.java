package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeliveryMapper {

    /**배송관리-배송관리 datatable 조회*/
    List<Map<String, Object>> selectDeliveryDatatable(Map<String, Object> map);
    Integer countDeliveryDatatable(Map<String, Object> map);


    /** 입고된 자산으로 송장 번호 확인 */
    Map<String, Object> selectDeliveryByGoods(Map<String, Object> map);

    /**주문 구성상품, 구분으로 송장 조회*/
    List<Map<String,Object>> selectOrderGsDnListByOgdnIdxAndOgdnType(Map<String, Object> map);
}

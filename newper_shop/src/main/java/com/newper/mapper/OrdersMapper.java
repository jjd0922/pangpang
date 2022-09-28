package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {
    /** mypage 주문상품 조회*/
    List<Map<String, Object>> selectOrderGsListByCuIdx (long cuIdx);
//
//    /** sp_idx 로 전시 분류 scate_idx, scate_name 조회 */
//    Map<String, Object> selectShopCategoryBySp(long sp_idx);
}

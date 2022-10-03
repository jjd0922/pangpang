package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {
    /** mypage 주문상품 조회*/
    List<Map<String, Object>> selectOrderGsListByCuIdx (long cuIdx);

    /** ph_idx 로 o_code 조회 */
    String selectOcodeByPhIdx(String ph_idx);

    /** o_code로 ogg_spo, cnt 가져오기 . 고객단 보여주기용 */
    List<Map<String, Object>> selectOrderGsGroupList(String o_code);
//
//    /** sp_idx 로 전시 분류 scate_idx, scate_name 조회 */
//    Map<String, Object> selectShopCategoryBySp(long sp_idx);
}

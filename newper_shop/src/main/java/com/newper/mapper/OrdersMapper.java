package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {
    /** mypage 주문상품 조회*/
    List<Map<String, Object>> selectOrderGsListByCuIdx (long cuIdx);


    /** mypage 주문상품 조회*/
    List<Map<String, Object>> selectOrderGsListByAsIdx (long asIdx);


    /**mypage 구매처 선택후 주문상품 조회*/

    List<Map<String, Object>> selectOrderGsListByComIdx (Map<String,Object> map);

    /** ph_idx 로 o_code 조회 */
    String selectOcodeByPhIdx(String ph_idx);

    /** o_code로 ogg_spo, cnt 가져오기 . 고객단 보여주기용 */
    List<Map<String, Object>> selectOrderGsGroupList(String o_code);

    List<Map<String,Object>> selectOGGForReview(@Param("cuId") String cuId,
                                                @Param("shopIdx") Integer shopIdx,
                                                @Param("start") Integer start,
                                                @Param("length") Integer length);

    /**oggIdx를 통해서 oIdx, spIdx 가져옴(map)*/
    Map<String,Object> selectOrderAndShopByOggIdx(Long oggIdx);
}

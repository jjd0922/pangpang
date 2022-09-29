package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChecksMapper {


    /** 입고검수그룹 조회*/
    List<Map<String, Object>> selectCheckGroupDatatable(Map<String, Object> map);
    /** 입고검수그룹 조회*/
    long countCheckGroupDatatable(Map<String, Object> map);

    /** 영업검수 조회*/
    List<Map<String, Object>> selectChecksDatatable(Map<String, Object> map);
    /** 영업검수 조회*/
    long countChecksDatatable(Map<String, Object> map);

    /** 입고검수 자산 조희 */
    List<Map<String, Object>> selectCheckGoods(Map<String, Object> map);
    long countCheckGoods(Map<String, Object> map);

    /** 해당 검수그룹 완료 체크 */
    int selectCheckGroupGoods(Map<String, Object> param);

    /** 해당 자산 검수 카운팅*/
    int selectCheckGoodsCount(@Param("gIdx") Long gIdx, @Param("cgsType") String cgsType);


}

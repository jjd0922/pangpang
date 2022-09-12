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
    List selectCheckGoods(Map<String, Object> map);

    /** 해당 자산 공정 회차 */
    int countCheckGroupByGoods(@Param("gIdx") Long gIdx);

    /** 해당 입고 자산들 공정예상비용 총합 */
    int selectCheckGroupExpectedCostTotal(int cgIdx);

    /** 해당 입고검수 자산 관계테이블 제거 */
    void deleteCheckGoodsByCG_IDX(int cgIdx);
}

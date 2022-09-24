package com.newper.mapper;

import com.newper.dto.ParamMap;
import com.newper.entity.ProcessNeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessMapper {

    /** 공정필요 조회 */
    List selectProcessNeed(Map<String, Object> map);

    ProcessNeed selectProcessNeedEntity(Map<String, Object> param);

    /** 공정그룹 조회 */
    List selectProcessGroupDatatable(Map<String, Object> map);

    /** 공정 그룹 카운트 */
    long countProcessGroupDatatable(Map<String, Object> map);

    /** 공정스펙 조회 */
    List<Map<String, Object>> selectProcessSpec(Map<String, Object> map);

    /** 반품그룹 - 자산 관계 데이터 생성 */
    void insertResellGoods(Map<String, Object> resellGoods);

    /** 임시 반품 그룹에 인덱스 값으로 해당 자산들의 매입처 조회 */
    Map<String, Object> selectResellComIdx(Map<String, Object> map);

    /** 반품그룹 조회 */
    List<Map<String, Object>> selectResellDatatable(Map<String, Object> map);

    /** 반품그롭 개수 조회 */
    long countResellDatatable(Map<String, Object> map);

    /** 자산 - 반품 조회 */
    List<Map<String, Object>> selectGoodsResell(Map<String, Object> map);

    /** 자산 - 반품 개수 조회 */
    long countGoodsResell(Map<String, Object> map);

    /** 자산 반품 */
    void updateResellGoods(Map<String, Object> map);

    /** 검수 그룹 데이터테이블 조회 */
    List selectCheckDatatable(Map<String, Object> map);

    /** 검수 그룹 개수 조회 */
    long countCheckDatatable(Map<String, Object> map);

    /** 해당자산 공정 필요 데이터 조회 */
    List<Map<String, Object>> selectProcessNeedDatatable(Map<String, Object> param);

    /** 해당자산 해당 공정 요청 가능한지 체크 */
    Map<String, Object> goodsProcessCheck(String gIdx, String type);

    void updateProcessNeed(ProcessNeed processNeed);

    /** 해당자산 공정완료시 남은 공정 개수 조회 */
    int selectProcessNeedByGoods(@Param("gIdx") Long gIdx);
}

package com.newper.mapper;

import com.newper.constant.PnType;
import com.newper.entity.ProcessNeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessMapper {

    /** 공정필요 조회 */
    Map<String, Object> selectProcessNeed(Map<String, Object> map);

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


    /** 검수 그룹 데이터테이블 조회 */
    List<Map<String, Object>> selectCheckDatatable(Map<String, Object> map);

    /** 검수 그룹 개수 조회 */
    long countCheckDatatable(Map<String, Object> map);

    /** 해당자산 공정 필요 데이터 조회 */
    List<Map<String, Object>> selectProcessNeedDatatable(Map<String, Object> param);

    /** 해당자산 해당 공정 요청 가능한지 체크 */
    Map<String, Object> goodsProcessCheck(String gIdx, String type);


    /** 해당자산 공정필요 공정타입 횟수 조회 */
    int selectProcessNeedCount(@Param("gIdx") Long gIdx, @Param("pnType") String pnType);

    /** 해당자산 공정필요 공정타입 마지막 데이터 0 처리 */
    void updateProcessNeedLast(@Param("gIdx") Long gIdx, @Param("pnType") String pnType);

    /** 임시그룹 자산 등록 */
    void insertGoodsTemp(@Param("gIdx") Long gIdx, @Param("ggtIdx") int ggtIdx);

    /** 해당자산 같은 공정 필요가 있는지 체크 */
    int checkProcessNeed(@Param("gIdx") Long gIdx, @Param("pnType") String pnType);

    /** 해당 임시그룹에있는 자산 인덱스 번호 조회 */
    List<Long> selectGoodsTemp(@Param("ggtIdx") long ggtIdx);

    /** 공정 조회 */
    List selectProcessNeedByGroup(Map<String, Object> map);

    /** 공정조회 */
    long countProcessNeedByGroup(Map<String, Object> map);

    /** 해당자산 다른 공정 필요가 있는지 체크 */
    int checkProcessNeedOtherByGoods(@Param("gIdx") Long gIdx);


    /** 해당공정그룹에 포함되어있는 완료되지 않은 자산 체크 */
    int checkProcessNeedOtherByChecks(@Param("cgIdx") int cgIdx);

    /** 재검수 그룹 자산 조회 */
    List<Map<String, Object>> selectReCheckGoods(Map<String, Object> map);

    /** 재검수 그룹 개수 조회 */
    long countReCheckGoods(Map<String, Object> map);


    /** 공정보드 조회 */
    List<Map<String, Object>> selectProcessDataTable(Map<String, Object> map);

    /** 공정보드 개수 조회 */
    long countProcessDataTable(Map<String, Object> map);

    /** 검수그룹안 데이터 조회 */
    List<Map<String, Object>> selectCheckGoodsDataTable(Map<String, Object> map);

    /** 검수그룹안 데이터 개수 조회 */
    long countCheckGoodsDataTable(Map<String, Object> map);

    /** AS 데이터 조회 */
    List<Map<String, Object>> selectAsCheckDatatable(Map<String, Object> map);

    /** AS 데이터 카운트 */
    long countAsCheckDatatable(Map<String, Object> map);


}

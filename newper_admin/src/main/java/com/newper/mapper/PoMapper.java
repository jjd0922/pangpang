package com.newper.mapper;

import com.newper.dto.ParamMap;
import com.newper.entity.PoProduct;
import com.newper.entity.PoReceived;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PoMapper {
    /** 발주 품의 조회 쿼리문 **/
    List<Map<String, Object>> selectPoDataTable(Map<String, Object> param);
    /** 발주 품의 조회 쿼리문 **/
    int countPoDataTable(Map<String, Object> param);

    /** 견적서 조회 쿼리문 */
    List<Map<String, Object>> selectEstimateDataTable(Map<String, Object> param);

    /** 견적서 갯수 조회 쿼리문 */
    long countEstimateDataTable(Map<String, Object> param);

    /**  견적서 - 상품 -상품분류 카테고리 - 브랜드 카테고리 조회 쿼리문 **/
    List<Map<String, Object>> selectEstimateProduct(@Param("peIdx") Integer peIdx);
    /**입고등록 datatable*/
    List<Map<String, Object>> selectInDatatable(Map<String, Object> param);
    /**입고등록 datatable count*/
    int countInDatatable(Map<String, Object> param);


    /** 발주 관리 조회 쿼리문 **/
    List<Map<String, Object>> selectPoApprovedDatatable(Map<String, Object> param);
    /** 발주 관리 조회 쿼리문 **/
    int countPoApprovedDatatable(Map<String, Object> param);
    /** 입고등록 팝업 group by 상품 . 조회 쿼리문 **/
    List<Map<String, Object>> selectInPoProductDatatable(Map<String, Object> param);
    /** 입고등록 팝업 조회 쿼리문 **/
    List<Map<String, Object>> selectInPoDatatable(Map<String, Object> param);
    /** 입고등록 팝업 상풉 입고 자산 조회 쿼리문 **/
    List<Map<String, Object>> selectInProductDatatable(Map<String, Object> param);
    /** 입고등록 팝업 상풉 입고 자산 조회 쿼리문 **/
    int countInProductDatatable(Map<String, Object> param);
    /** 입고등록 팝업 바코드 등록 쿼리문 **/
    public void updategoods(@Param("po_idx") int po_idx, @Param("p_idx") int p_idx);

    /** 발주서에 상품idx조회 */
    List<Integer> selectPoProductIdxList(@Param("poIdx") int poIdx);

    /** 입고등록 팝업 발주상품 자산 조회 쿼리문 **/
    List<Map<String, Object>> selectInPpDatatable(Map<String, Object> param);
    /** 입고등록 팝업 발주상품 자산 조회 쿼리문 **/
    int countInPpDatatable(Map<String, Object> param);

    /** 발주품의 상품 조회 쿼리문 */
    List<Map<String, Object>> selectPoProductByPoIdx(@Param("poIdx") long poIdx);

    /** 발주품의 상품 개수 조회 쿼리문 */
    long countPoProductByPoIdx(@Param("poIdx") long poIdx);

    /** 검수리포트 실입고 상품 있는지 확인 */
    PoReceived selectPoReceivedByProductAndPoAndSpecAndOption(@Param("pIdx") int pIdx, @Param("poIdx") int poIdx, @Param("specIdx") int specIdx, @Param("option") String option);

    /** 해당 발주의 실입고 상품 select */
    void selectReceivedByPo(Map<String, Object> map);

    /** 발주품의 실입고상품 조회 쿼리문 */
    List<Map<String, Object>> selectPoReceivedByPoIdx(int poIdx);

    /** 상품 타입이 정상품일경우 자동매핑을 위한 실입고상품 조회문 */
    PoReceived selectPoReceivedByPoIdxAndPpIdx(@Param("poIdx") int po_idx, @Param("ppIdx") Integer ppIdx);
    
    /**po수정시 연결되어있던 pp모두 삭제 > 그이후 다시 등록할 것*/
    void deletePoProductBypoIdx(@Param("poIdx") Integer poIdx, @Param("ppIdxs") List<Integer> ppIdxs);
}

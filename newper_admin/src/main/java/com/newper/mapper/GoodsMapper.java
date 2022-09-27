package com.newper.mapper;

import com.newper.constant.GState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {

    List<Map<String, Object>> selectWareHouseDataTable(Map<String, Object> map);

    Integer countWareHouseDataTable(Map<String, Object> map);

    /** 공정보드 datatable*/
    List<Map<String, Object>> selectProcessBoardDatatable(Map<String, Object> map);
    /** 공정보드 datatable*/
    long countProcessBoardDatatable(Map<String, Object> map);

    /** 임시 테이블에 insert */
    void insertGoodsTemp(@Param("ggt_idx") String ggt_idx, @Param("g_idxs") String[] g_idxs);

    /** 자산 임시 테이블 조회*/
    List<Map<String, Object>> selectGoodsTempDatatable(Map<String, Object> map);
    /** 자산 임시 테이블 조회*/
    Long countGoodsTempDatatable(Map<String, Object> map);

    /** 입고검수 임시 테이블 제거 **/
    void deleteGoodsGroupTempByGIdxAndGgtIdx(Map<String, Object> map);

    Map<String, Object> selectGoodsByG_IDX(Map<String, Object> param);

    /** 입고검수 임시 테이블 삭제 **/
    void deleteGoodsGroupTempByGGT_IDX(@Param("ggtIdx") long ggt_idx);

    /** 자산조회 */
    List<Map<String,Object>> selectGoodsDataTable(Map<String, Object> map);

    /** 자산 개수 조회 */
    long countGoodsDataTable(Map<String, Object> map);

    /** 바코드 조회 */
    List<Map<String, Object>> selectBarcodeDataTable(String barcode);


    /** 자산으로 같은 상품의 데이터 체크 */
    List<Map<String, Object>> goodsProductCheck(Map<String, Object> map);

    /** 자산으로 같은 입고그룹의 데이터체크 */
    List<Map<String, Object>> checkGoodsReceived(Map<String, Object> map);

    /** 자산의 발주의 매입처 정보로 GROUP BY */
    List<Map<String, Object>> selectGoodsGroupByPO_COMPANY(Map<String, Object> map);

    /** 자산 반품요청값으로 GROUP BY */
    List<Map<String, Object>> selectGoodsGroupByState(Map<String, Object> map);

    /** 해당 검수그룹에 해당하는 자산 조회 */
    List<Long> selectGoodsByCheckGroup(@Param("cgIdx") int cgIdx);

    /** 자산들 상태값 변경 */
    void updateGoodsState(@Param("gIdx") List<Long> gIdx, @Param("gState") String gState);


    List<Map<String, Object>> selectGoodsByPo(int poIdx);

    /** 해당 발주건에 해당하는 자산들이 실입고 그룹으로 매핑되어있는지 체크 */
    int checkGoodsPoReceived(int poIdx);
}

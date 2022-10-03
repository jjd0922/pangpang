package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CalculateMapper {

    List<Map<String, Object>> selectVendorSettingDatatable(Map<String, Object> param);

    Long countVendorSettingDatatable(Map<String, Object> param);


    /** 정산그룹 조회 */
    List<Map<String, Object>> selectCalculateGroupDatatable(Map<String, Object> map);

    /** 정산그룹 카운트 */
    long countCalculateGroupDatatable(Map<String, Object> map);

    /** 매출정산조회 */
    List<Map<String, Object>> selectOrderGsDatatable(Map<String, Object> map);

    /** 매출정산 카운트 **/
    long countOrderGsDatatable(Map<String, Object> map);

    /** 매출 조정액 조회 */
    List<Map<String, Object>> selectAdjust(Map<String, Object> map);

    /** excel 행 넘버 알파벳 번호 **/
    List<Map<String, Object>> selectExcelColumn();
}

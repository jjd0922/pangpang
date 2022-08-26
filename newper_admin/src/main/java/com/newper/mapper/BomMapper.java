package com.newper.mapper;

import com.newper.dto.ParamMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BomMapper {

    /**bom 데이터테이블 조회*/
    List<Map<String, Object>> selectBomDatatable(Map<String, Object> map);
    long countBomDatatable(Map<String, Object> map);

    /** bom 등록 */
    void insertBom(Map<String, Object> map);

    /** bom 자상품 select */
    List<Map<String, Object>> selectBomChild(Integer mpIdx);

    /** bom 삭제 >> bom 수정 시 사용: 수정하려는 bom의 모상품과 연결된 데이터를 삭제 후 다시 insert함 */
    void deleteBom(Map<String, Object> map);

    /** bom 일괄 삭제*/
    void deleteBomAll(Map<String, Object> map);

    /** 모상품으로 bom count*/
    int countBom(Map<String, Object> map);
}

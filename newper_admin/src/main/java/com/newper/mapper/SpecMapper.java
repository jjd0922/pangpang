package com.newper.mapper;

import com.newper.entity.SpecList;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SpecMapper {

    /** specl_name으로 value목록 가져오기*/
    List<String> selectSpecListValueList(String speclName);

    /** specl_name으로 specl_value (key)  가져오기*/
    @MapKey(value = "speclValue")
    Map<String, SpecList> selectSpecListMap(String speclName);

    /** spec_item insert **/
    void insertSpecItem(Map<String, Object> specParam);

    void insertSpecItemAll(@Param("specIdx") Integer specIdx,
                           @Param("speclIdxs") int[] speclIdxs);


    /**재고 자산 상세 스펙이력 데이터 테이블*/
    List<Map<String, Object>> selectSpecDataTable(Map<String, Object> param);
    /**재고 자산 상세 스펙이력 데이터 테이블*/
    Integer countSpecDataTable(Map<String, Object> param);
}

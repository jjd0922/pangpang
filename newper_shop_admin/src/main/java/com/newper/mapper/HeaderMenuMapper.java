package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HeaderMenuMapper {

    /** GNB 메뉴 dataTables && count >> 분양몰 갯수만 맞춰줌 */
    List<Map<String, Object>> selectMainMenuDatatable(Map<String, Object> map);
    Map<String, Object> countMainMenuDatatable(Map<String, Object> map);
    /** 분양몰에 해당하는 GNB 메뉴 갯수*/
    Integer countMainMenu(@Param("shopIdx") Integer shopIdx);
}

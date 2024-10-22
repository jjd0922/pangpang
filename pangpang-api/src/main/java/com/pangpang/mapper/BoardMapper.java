package com.pangpang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    @Transactional(readOnly = true)
    List<Map<String, Object>> selectBoardList(@Param("CATE_IDX") String CATE_IDX,
                                              @Param("CU_IDX") String CU_IDX,
                                              @Param("BD_STATE") String BD_STATE,
                                              @Param("BD_RATE") String BD_RATE,
                                              @Param("BD_START_DATE") String BD_START_DATE,
                                              @Param("LIMIT") int LIMIT,
                                              @Param("PAGE") int PAGE);
    @Transactional(readOnly = true)
    Map<String, Object> selectBoardDetail(@Param("bdIdx") String bdIdx);

    @Transactional(readOnly = true)
    String selectBoardBtnState(@Param("bdIdx") String bdIdxm,@Param("cuIdx") String cuIdx,@Param("type") String type);
}

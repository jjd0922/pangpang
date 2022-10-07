package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AfterServiceMapper {


    /** mypage AS신청현황 조회*/
    List<Map<String, Object>> selectAsListByAsIdx (long asIdx);


}

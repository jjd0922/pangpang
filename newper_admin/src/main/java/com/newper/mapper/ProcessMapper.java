package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProcessMapper {

    /** 공정필요 조회 */
    List selectProcessNeed(Map<String, Object> map);
}

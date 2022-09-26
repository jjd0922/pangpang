package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MainSectionMapper {
    List<Map<String, Object>> selectMainSectionSp(@Param("shopIdx") Integer shopIdx);
}

package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    List<Map<String, Object>> selectNoticeDatatable(Map<String, Object> map);

    long countNoticeDatatable(Map<String, Object> map);
}

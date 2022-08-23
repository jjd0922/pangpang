package com.newper.mapper;

import com.newper.dto.ParamMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BomMapper {

    List<Map<String, Object>> selectBomDatatable(Map<String, Object> map);
    long countBomDatatable(Map<String, Object> map);

    void insertBom(Map<String, Object> map);
}

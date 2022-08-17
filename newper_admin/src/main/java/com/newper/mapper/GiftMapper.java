package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GiftMapper {

    List<Map<String, Object>> selectGiftGroupDataTable(Map<String, Object> map);

    long countGiftGroupDataTable(Map<String, Object> map);

    List<Map<String, Object>> selectGiftDataTable(Map<String, Object> map);

    long countGiftDataTable(Map<String, Object> map);
}

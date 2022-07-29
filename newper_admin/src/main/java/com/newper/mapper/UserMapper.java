package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserMapper {
    List<Map<String, Object>> selectUserDatatable(Map<String, Object> param);

    Integer countUserDatatable(Map<String, Object> param);

    List<Map<String, Object>> selectUser(Map<String, Object> param);

    long countUser(Map<String, Object> param);
}

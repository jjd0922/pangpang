package com.pangpang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Mapper
@Transactional
public interface CustomerMapper {

    @Transactional(readOnly = true)
    Map<String, Object> selectCustomerInfo(@Param("cuIdx") String cuIdx);
}

package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CustomerMapper {

    /** 아이디 찾기 */
    Map<String,Object> selectCustomerByCuCi(Map<String,Object> map);
}

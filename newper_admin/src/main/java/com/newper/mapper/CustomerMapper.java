package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {

    /**회원관리 datatable 조회*/
    List<Map<String, Object>> selectCustomerDatatable(Map<String, Object> map);
    long countCustomerDatatable(Map<String, Object> map);
}

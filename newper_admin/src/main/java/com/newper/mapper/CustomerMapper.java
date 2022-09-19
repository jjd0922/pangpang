package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {

    /**회원관리 datatable 조회*/
    List<Map<String, Object>> selectCustomerDatatable(Map<String, Object> map);
    long countCustomerDatatable(Map<String, Object> map);

    /**발송내역 조회테이블*/
    List<Map<String, Object>> selectSendingHistoryDatatable(Map<String, Object> map);

    long countSendingHistoryDatatable(Map<String, Object> map);
}

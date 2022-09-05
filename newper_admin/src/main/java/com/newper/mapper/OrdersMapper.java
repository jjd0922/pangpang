package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {

    /**통합주문관리 dataTable*/
    List<Map<String, Object>> selectOrderDatatable(Map<String, Object> map);
    Integer countOrderDatatable(Map<String, Object> map);

}

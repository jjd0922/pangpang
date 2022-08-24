package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopProductMapper {

    /**샵상품 dataTable*/
    List<Map<String, Object>> selectShopProductDatatable(Map<String, Object> map);
    Integer countShopProductDatatable(Map<String, Object> map);

}

package com.newper.mapper;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ShopProductMapper {

    /** ShopProductOption 조회 */
    List<Map<String, Object>> selectShopProductOptionList(long sp_idx);
}

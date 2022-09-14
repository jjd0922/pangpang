package com.newper.mapper;

import com.newper.constant.LocType;
import com.newper.constant.WhState;
import com.newper.entity.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface WarehouseMapper {

    /**창고 데이터테이블 조회*/
    List<Map<String,Object>> selectWarehouseDatatable(Map<String, Object> map);
    long countWarehouseDatatable(Map<String, Object> map);

    /**창고 상태 일괄변경*/
    void changeAllWhState(@Param("whIdxs") String[] whIdxs,
                          @Param("whState") WhState whState);

    /**창고 내 재고자산 개수*/
    int countGoodsInWarehouse(Integer whIdx);
    
    /**로케이션 내 재고자산 개수*/
    int countGoodsInLocation(Integer locIdx);
}

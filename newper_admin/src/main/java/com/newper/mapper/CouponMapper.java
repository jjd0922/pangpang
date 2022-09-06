package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CouponMapper {

    /**쿠폰그룹데이터테이블 조회*/
    public List<Map<String, Object>> selectCouponGroupDatatable(Map<String, Object> map);
    public long countCouponGroupDatatable(Map<String, Object> map);

    /**cpgIdx에 속한 쿠폰들 한번에 등록*/
    public void insertCoupon(@Param("cpgIdx") long cpgIdx,
                           @Param("cpgCnt") int cpgCnt);

    /**cpgIdx별 쿠폰리스트*/
    public List<Map<String, Object>> selectCouponDatatable(Map<String, Object> map);
    public long countCouponDatatable(Map<String, Object> map);

}
package com.newper.mapper;

import com.newper.constant.GiftState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CouponMapper {

    public void insertGift(@Param("cpgIdx") long cpgIdx,
                           @Param("cpgCnt") int cpgCnt);

}
package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {

    void updatePaymentHistoryResult(@Param("ph_idx") String ph_idx, @Param("ph_res") String ph_res);
}

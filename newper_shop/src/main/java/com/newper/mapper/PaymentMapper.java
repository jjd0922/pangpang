package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface PaymentMapper {

    /** uid만 업데이트 후 결제 결과는 따로 조회*/
    void updatePaymentHistoryUid(@Param("ph_idx") String ph_idx, @Param("ph_uid") String ph_uid);
    /**o_code, ph_type.name() 으로 결제 응답 조회*/
    Map<String,Object> selectPaymentHistoryRes(@Param("o_code") String o_code, @Param("ph_type") String ph_type_name);


}

package com.newper.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentMapper {

    /** uid만 업데이트 후 결제 결과는 따로 조회*/
    void updatePaymentHistoryUid(@Param("ph_idx") String ph_idx, @Param("ph_uid") String ph_uid);


}

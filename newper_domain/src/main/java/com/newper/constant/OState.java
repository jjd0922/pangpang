package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OState implements EnumOption {

     BEFORE("입금대기")
    ,DONE("주문완료")
    ,READY("상품준비중")
    ,DELIVERY_REQ("배송요청")
    ,INSTALL_REQ("설치요청")
    ,ING("배송중")
    ,DELIVERY_DONE("배송완료")
    ,INSTALL_DONE("설치완료")
    ,RETURN_REQ("반품요청")
    ,RETURN_DONE("반품완료")
    ;

    private String option;
}

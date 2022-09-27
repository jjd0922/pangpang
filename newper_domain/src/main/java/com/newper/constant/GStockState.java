package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**GOODS.G_STOCK_STATE*/
@Getter
@AllArgsConstructor
public enum GStockState implements EnumOption {

     N("미적재")
    ,STOCK_REQ("재고인계요청")
    ,STOCK("가용재고")
    ,OUT_REQ("출고재고")
    ,OUT("출고완료")
    ,MOVE("자산이동중")
    ,AS("AS")
    ,REFUND("반품")
    ;

    private String option;
}

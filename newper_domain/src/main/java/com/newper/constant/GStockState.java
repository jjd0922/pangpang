package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**GOODS.G_STOCK_STATE*/
@Getter
@AllArgsConstructor
public enum GStockState implements EnumOption {

     N("상품화전 재고")
    ,STOCK("가용재고")
    ,OUT_REQ("출고재고")
    ,OUT("출고완료")
    ,MOVE("자산이동중")
    ,AS("AS")
    ;

    private String option;
}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**GOODS.G_STOCK_STATE*/
@Getter
@AllArgsConstructor
public enum GStockState implements EnumOption {

    /**미적재*/
    N("미적재")
    /**가용재고*/
    ,STOCK("가용재고")
    /**출고재고*/
    ,OUT_STOCK("출고재고")
    ;

    private String option;
}

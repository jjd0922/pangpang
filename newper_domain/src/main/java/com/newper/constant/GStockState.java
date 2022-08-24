package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**GOODS.G_STOCK_STATE*/
@Getter
@AllArgsConstructor
public enum GStockState implements EnumOption {

     N("미적재")
    ,STOCK("가용재고")
    ,OUT_STOCK("출고재고")
    ;

    private String option;
}

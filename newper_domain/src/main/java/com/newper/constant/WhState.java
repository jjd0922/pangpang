package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**창고 상태; 정상,미사용*/
@Getter
@AllArgsConstructor
public enum WhState implements EnumOption {

    /** 정상*/
    NORMAL("정상")
    /** 미사용*/
    ,DISUSE("미사용")
    ;

    private String option;

}

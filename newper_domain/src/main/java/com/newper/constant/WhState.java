package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**창고 상태; 정상,미사용*/
public enum WhState implements EnumOption {

    NORMAL("정상")
    ,DISUSE("미사용");

    private String option;

}

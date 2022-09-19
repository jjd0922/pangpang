package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
/**admin 상담결과 상담구분*/
@Getter
@AllArgsConstructor
public enum CrType implements EnumOption {

    IN("내부")
    ,OUT("외부");

    private String option;
}

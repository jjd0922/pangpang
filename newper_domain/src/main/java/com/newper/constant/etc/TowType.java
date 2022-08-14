package com.newper.constant.etc;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TowType{

        Y(true)
        ,N(false);

        private boolean option;


}

package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 등급 .  GOODS.G_RANK , GOODS_STOCK.GS_RANK*/
@Getter
@AllArgsConstructor
public enum GRank implements EnumOption {

    N1("새상품", "-")
    ,S1("박스개봉", "S")
    ,S2("단기사용", "S")
    ,S3("해외리퍼", "S")
    ,A1("A급리퍼", "A")
    ,B1("가성비중고", "B")
    ,E1("임박상품", "-")
    ;

    private String option;
    private String grade;
}

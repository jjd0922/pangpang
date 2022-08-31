package com.newper.constant;

import com.newper.constant.basic.EnumOption;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** 등급 .  GOODS.G_RANK , GOODS_STOCK.GS_RANK*/
@Getter
@AllArgsConstructor
public enum GRank implements EnumOption {

    /** 새상품*/
    N1("새상품", "-")
    /** 박스개봉*/
    ,S1("박스개봉", "S")
    /** 단기사용*/
    ,S2("단기사용", "S")
    /** 해외리퍼*/
    ,S3("해외리퍼", "S")
    /** A급리퍼*/
    ,A1("A급리퍼", "A")
    /** 가성비중고*/
    ,B1("가성비중고", "B")
    /** 임박상품*/
    ,E1("임박상품", "-")
    ;

    private String option;
    private String grade;
}

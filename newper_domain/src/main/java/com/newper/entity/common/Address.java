package com.newper.entity.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String post;        // 우편번호, 주소
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;

}

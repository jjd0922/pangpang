package com.newper.entity.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
/** 주소 embedded class*/
public class Address {

    private String post;
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;

}

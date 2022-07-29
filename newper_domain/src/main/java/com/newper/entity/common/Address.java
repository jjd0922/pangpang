package com.newper.entity.common;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
/** 주소 embedded class*/
public class Address {
    private String post;
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;
}

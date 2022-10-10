package com.newper.entity.common;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
public class AddressEmb {
    private String post;
    private String addr1;
    private String addr2;
    private String addr3;
    private String addr4;

    /** 주소1,2,3 문자열*/
    public String address3(){
        return getAddr1() + " " + addr2 + " " + addr3;
    }
}

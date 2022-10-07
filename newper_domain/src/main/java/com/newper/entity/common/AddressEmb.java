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
}

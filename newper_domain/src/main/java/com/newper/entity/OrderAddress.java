package com.newper.entity;


import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderAddress{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oaIdx;

    private String oaName;

    private String oaPhone;

    private String oaMemo;

    private String oaEntrance;

    @Embedded
    private Address address;


    @PrePersist
    @PreUpdate
    public void ordersAddressSave(){

        if (!StringUtils.hasText(getOaName())) {
            throw new MsgException("배송자(설치자) 이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(getOaPhone())) {
            throw new MsgException("배송자(설치자) 연락처를 입력해주세요.");
        }
        if (!StringUtils.hasText(getAddress().getPost())) {
            throw new MsgException("배송지를 입력해주세요.");
        }

    }

}

package com.newper.entity;


import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
    private Long adIdx;

    private String adName;

    private String adPhone;

    private String adMemo;

    private String adEntrance;

    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderAddress")
    private List<Orders> ordersList;

    @PrePersist
    @PreUpdate
    public void ordersAddressSave(){

        if (!StringUtils.hasText(getAdName())) {
            throw new MsgException("배송자(설치자) 이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(getAdPhone())) {
            throw new MsgException("배송자(설치자) 연락처를 입력해주세요.");
        }
        if (!StringUtils.hasText(getAddress().getPost())) {
            throw new MsgException("배송지를 입력해주세요.");
        }

    }
}

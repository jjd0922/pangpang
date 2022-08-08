package com.newper.entity;


import com.newper.entity.common.Address;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
    private Long adIdx;

    private String adName;

    private String adPhone;

    private String adMemo;

    private String adEntrance;

    @Embedded
    private Address address;

}

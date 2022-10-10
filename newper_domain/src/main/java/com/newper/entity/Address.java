package com.newper.entity;

import com.newper.entity.common.AddressEmb;
import com.newper.entity.common.BaseEntityWithoutBy;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address extends BaseEntityWithoutBy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private boolean adBasic;
    private String adTitle;

    @Embedded
    private AddressEmb address;

    private String adPhone;
    private String adMemo;
    private String adEntrance;
    private String adName;

    /** adPhone 휴대폰 format으로 */
    public String adPhoneFormat(){
        return getAdPhone().replaceAll("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
    }
}

package com.newper.entity;

import com.newper.constant.CdType;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
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
public class CompanyDelivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COM_IDX2", referencedColumnName = "comIdx")
    private Company company2;

    private byte cdBasic;

    @Enumerated(EnumType.STRING)
    private CdType cdType;

    private int cdFree;
    private int cdFee;
    private int cdJeju;
    private int cdEtc;
    private int cdReturnFee;


    @PrePersist
    @PreUpdate
    public void preSave(){
        if (getCdType() == null) {
            throw new MsgException("배송비/설치비 종류설정을 선택해주세요.");
        }

    }

}

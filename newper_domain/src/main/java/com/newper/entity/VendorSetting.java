package com.newper.entity;

import com.newper.constant.VsDeliveryCal;
import com.newper.constant.VsType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "vendor_setting")
public class VendorSetting extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer vsIdx;

    @Enumerated(EnumType.STRING)
    private VsType vsType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VS_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String vsDeliveryLine;

    @Enumerated(EnumType.STRING)
    private VsDeliveryCal vsDeliveryCal;

    private String vsOrder;

    private String vsProduct;

    private String vsPrice;

    private String vsDeliveryCost;

    private String vsFee;

    private String vsRealPrice;

   /* private String vsCal;*/

    private String vsMemo;

    private LocalDate modifiedDate;

    private LocalDate createdDate;
}

package com.newper.entity;

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
public class CalculateSetting {
    @Id
    @GeneratedValue
    private Integer csIdx;

    private String csType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_C_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String csDeliveryLine;

    private String csDeliveryCal;

    private String csOrder;

    private String csProduct;

    private String csPrice;

    private String csDeliveryCost;

    private String csFee;

    private String csRealPrice;

    private String csCal;

    private String csMemo;

    private LocalDate csDate;

    private LocalDate csLog;
}
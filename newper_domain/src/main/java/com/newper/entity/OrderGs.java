package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderGs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ogIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_SPO_IDX", referencedColumnName = "spoIdx")
    private ShopProductOption shopProductOption;

    private String ogType;

    private Integer ogPrice;

    private Date ogDate;

    private Time ogTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OG_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNumber deliveryNumber;







}
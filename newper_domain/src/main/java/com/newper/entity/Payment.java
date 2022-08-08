package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payIdx;

    private String payState;

    private String payCancelDate;

    private Integer payPrice;

    private Integer payProductPrice;

    private Integer payPoint;

    private Integer payUseMileage;

    private Integer payCoupon;

    private Integer payDelivery;

    private String payMethod;

    private Integer payMileage;

    private Long phIdx;

    private Long phIdx2;

}

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
public class Calculate {
    @Id
    @GeneratedValue

    private Integer caIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CA_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CA_O_IDX", referencedColumnName = "oIdx")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CA_P_IDX", referencedColumnName = "pIdx")

    private Product product;

    private String caState;

    private String caCode;

    private Integer caPrice;

    private Integer caDeliveryCost;

    private Integer caFee;

    private Integer caCalPrice;

    private LocalDate caDate;

    private String caDateMonth;

    private LocalDate caCancelDate;




}

package com.newper.entity;

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
public class OrderCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long ocIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OC_O_IDX", referencedColumnName = "oIdx")
    private Order order;



}

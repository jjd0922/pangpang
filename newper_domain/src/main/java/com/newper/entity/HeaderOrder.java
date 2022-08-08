package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import com.newper.entity.common.ModifiedEntity;
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

public class HeaderOrder extends ModifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hoIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FO_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private int hoRow;

    private int hoCol;



}

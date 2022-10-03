package com.newper.entity;

import com.newper.constant.etc.HoType;
import com.newper.entity.common.ModifiedEntity;
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

public class HeaderOrder extends ModifiedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hoIdx;

    @Enumerated(EnumType.STRING)
    private HoType hoType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HO_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private int hoRow;

    private int hoCol;



}

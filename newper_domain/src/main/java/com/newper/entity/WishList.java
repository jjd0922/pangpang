package com.newper.entity;

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
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wlIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WL_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WL_SP_IDX", referencedColumnName = "spIdx")
    private ShopProduct shopProduct;


}

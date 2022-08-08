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
public class ShopProductAdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spaIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPA_SP_IDX", referencedColumnName = "spIdx")
    private ShopProduct shopProduct;

    private String spaName;

    private String spaCheck;

    private Integer spaCount;

}

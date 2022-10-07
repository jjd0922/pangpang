package com.newper.entity;

import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShopProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spoIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPO_SPA_IDX", referencedColumnName = "spaIdx")
    private ShopProductAdd shopProductAdd;

    /** 옵션 해당사항없음 의 경우 null일 수 있음*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPO_GS_IDX", referencedColumnName = "gsIdx")
    private GoodsStock goodsStock;

    @Builder.Default
    private int spoDepth = 1;
    private String spoName;

    private int spoShopPrice;
    private int spoPrice;
    /** 판매 여부 */
    private boolean spoSell;

    @PrePersist
    @PreUpdate
    public void spoSave(){


    }

}

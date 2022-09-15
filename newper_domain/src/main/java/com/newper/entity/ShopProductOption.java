package com.newper.entity;

import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShopProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer spoIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPO_SPA_IDX", referencedColumnName = "spaIdx")
    private ShopProductAdd shopProductAdd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPO_GS_IDX", referencedColumnName = "gsIdx")
    private GoodsStock goodsStock;

    private String spoName;

    private Integer spoPrice;

    @PrePersist
    @PreUpdate
    public void spoSave(){

        if (getGoodsStock() == null) {
            throw new MsgException("재고상품을 선택해주세요.");
        }


    }

}

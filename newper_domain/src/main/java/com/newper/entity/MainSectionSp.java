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
public class MainSectionSp {

    @EmbeddedId
    private MainSectionSpEmbedded msspIdx;

    /** 메인섹션 idx*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSSP_MS_IDX", referencedColumnName = "msIdx")
    @MapsId("msIdx")
    private MainSection mainSection;
    /** 분양몰상품 idx*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSSP_SP_IDX", referencedColumnName ="spIdx")
    @MapsId("spIdx")
    private ShopProduct shopProduct;

    /** 순서*/
    private int msspOrder;
}

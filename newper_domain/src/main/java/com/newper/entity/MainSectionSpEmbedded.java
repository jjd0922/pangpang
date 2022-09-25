package com.newper.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class MainSectionSpEmbedded implements Serializable {

    public MainSectionSpEmbedded(MainSection mainSection, ShopProduct shopProduct){
        this.mainSection = mainSection;
        this.shopProduct = shopProduct;
    }

    /** 메인섹션 idx*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSSP_MS_IDX", referencedColumnName = "msIdx")
    private MainSection mainSection;
    /** 분양몰상품 idx*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSSP_SP_IDX", referencedColumnName ="spIdx")
    private ShopProduct shopProduct;
}

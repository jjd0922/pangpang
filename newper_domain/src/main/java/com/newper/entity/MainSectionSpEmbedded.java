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

    private Long msIdx;
    private Long spIdx;
//    public MainSectionSpEmbedded(MainSection mainSection, ShopProduct shopProduct){
//        this.mainSection = mainSection;
//        this.shopProduct = shopProduct;
//    }


}

package com.newper.entity;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CategoryShopCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cscIdx;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cscCateIdx", referencedColumnName = "cateIdx")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cscScateIdx", referencedColumnName = "scateIdx")
    private ShopCategory shopCategory;


    private int cscOrder;


    /** order 업데이트.*/
    public void updateCscOrder(int cscOrder){
        if(getCscOrder() < 0){
            setCscOrder(cscOrder * -1);
        }else{
            setCscOrder(cscOrder);
        }
    }



}

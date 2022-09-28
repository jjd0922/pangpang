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
public class ShopProductAdd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spaIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SPA_SP_IDX", referencedColumnName = "spIdx")
    private ShopProduct shopProduct;

    private String spaName;

    /** 필수 여부*/
    private boolean spaRequired;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shopProductAdd", cascade = CascadeType.ALL)
    @OrderBy(value = "spoIdx asc")
    private List<ShopProductOption> shopProductOptionList;

}

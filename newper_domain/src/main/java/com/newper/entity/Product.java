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
public class Product extends BaseEntity {

@Table (name = "product")
public class Product {

    @Id
    private Integer pIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX", referencedColumnName = "cateIdx")
    private  Category category;
    private long p_sell_price;
    private String p_code;
    private String p_name;
    private String p_model;


    private String pCode;
    private Integer pPrice;
    private Integer pNaverPrice;
    private String pType1;
    private String pType2;
    private String pName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pInfo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pOption;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX2", referencedColumnName = "cateIdx")
    private Category category2;
}


}

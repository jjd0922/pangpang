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
@Table (name = "product")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pIdx;

    private long p_sell_price;
    private String p_code;
    private String p_name;
    private String p_model;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX2", referencedColumnName = "cateIdx")
    private Category category2;
}



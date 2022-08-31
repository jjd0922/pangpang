package com.newper.entity;

import com.newper.constant.ShopState;
import com.newper.constant.ShopType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Shop extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopIdx;


    @Enumerated(EnumType.STRING)
    private ShopState shopState;

    private String shopName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ShopType shopType = ShopType.NORMAL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_PG_IDX", referencedColumnName = "pgIdx")
    private Pg pg;

//    private String shopPoint;
//
    private Float shopMileage;

    private String shopBasket;
//
//    private String shopHdMeta;
//
//    private String shopHdLoginGroup;
//
//    private String shopDesign;


    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Domain> domainlist = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.DETACH)
    @OrderBy(value = "hmOrder asc")
    private List<HeaderMenu> headerMenulist = new ArrayList<>();







}

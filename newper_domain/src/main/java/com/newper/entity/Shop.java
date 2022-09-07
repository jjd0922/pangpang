package com.newper.entity;

import com.newper.constant.ShopState;
import com.newper.constant.ShopType;
import com.newper.converter.ConvertMap;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    /** 운영상태*/
    @Enumerated(EnumType.STRING)
    private ShopState shopState;
    /** 분양몰명*/
    private String shopName;

    /** 쇼핑몰타입*/
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ShopType shopType = ShopType.NORMAL;
    /** PG사이트코드*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHOP_PG_IDX", referencedColumnName = "pgIdx")
    private Pg pg;
    /** 포인트 사용여부*/
//    private String shopPoint;
    /** 마일리지 사용여부*/
    private Float shopMileage;
    /** 장바구니 사용여부*/
    private String shopBasket;
    /** 메타태그*/
//    private String shopHdMeta;
    /** 로그인그룹 디스플레이 항목*/
//    private String shopHdLoginGroup;
    /** 분양몰 디자인 (JSON MAP)*/
    @Convert(converter = ConvertMap.class)
    @Builder.Default
    private Map<String,Object> shopDesign = new HashMap<>();

    /** 도메인 리스트*/
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Domain> domainlist = new ArrayList<>();
    /** 헤더메뉴 리스트*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.DETACH)
    @OrderBy(value = "hmOrder asc")
    private List<HeaderMenu> headerMenulist = new ArrayList<>();

    @PrePersist
    public void savePre(){
        if(StringUtils.hasText(getShopName())){
            throw new MsgException("분양몰명을 입력해주세요.");
        }else if(shopState == null){
            throw new MsgException("운영 상태를 선택해주세요.");
        }else if(shopType == null){
            throw new MsgException("쇼핑몰 타입을 선택해주세요.");
        }
    }





}

package com.newper.entity;

import com.newper.constant.ShopState;
import com.newper.constant.ShopType;
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
    /** 분양몰 디자인 (JsonString)
     * key : value
     * ShopDesign Enum 에서 확인
     * */
    @Builder.Default
    private Map<String,Object> shopDesign = new HashMap<>();

    /**value = shopHdLoginGroup.name()*/
    @Builder.Default
    private List<String> shopHdLoginGroup=new ArrayList<>();

    /** 도메인 리스트*/
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    private List<Domain> domainlist = new ArrayList<>();
    /** 헤더메뉴 리스트*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.DETACH)
    @OrderBy(value = "hmOrder asc")
    private List<HeaderMenu> headerMenulist = new ArrayList<>();
    /** 분양몰 헤더 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.DETACH)
    @OrderBy(value = "hoCol, hoRow")
    private List<HeaderOrder> headerOrderList = new ArrayList<>();
    /** 분양몰 플로팅바 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.ALL)
    @OrderBy(value = "abs(fbDisplay) asc")
    @Builder.Default
    private List<FloatingBar> floatingBarList = new ArrayList<>();
    /** 이벤트그룹 리스트*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop", cascade = CascadeType.DETACH)
    private List<EventGroup> eventGroupList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shop")
    private List<CouponGroup> couponGroups;

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

    public void addFloatingBar(FloatingBar fb){
        getFloatingBarList().add(fb);
        fb.setShop(this);
    }
}

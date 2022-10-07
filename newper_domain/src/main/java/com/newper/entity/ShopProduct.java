package com.newper.entity;

import com.newper.constant.SpState;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ShopProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SP_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    /** 중분류*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SP_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    private String spName;
    private String spInfo;
    private String spMd;

    @Enumerated(EnumType.STRING)
    private SpState spState;
    private int spUseMileage;
    private int spUseCoupon;
    private Float spPercent;
    private Integer spQuotaOnce;
    private Integer spQuotaId;
    private byte spOnlyApp;
    private int spMinimum;
    private String spTag;

    private LocalDate spShowStartDate;
    private LocalTime spShowStartTime;
    private LocalDate spShowEndDate;
    private LocalTime spShowEndTime;
    private LocalDate spSellStartDate;
    private LocalTime spSellStartTime;
    private LocalDate spSellEndDate;
    private LocalTime spSellEndTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shopProduct", cascade = CascadeType.ALL)
    @OrderBy("rIdx desc")
    private List<Review> reviews;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shopProduct", cascade = CascadeType.ALL)
    private List<QnaSp> qnaSpList;

    /** <pre>
     * key : value
     *
     * </pre>*/
    private Map<String,Object> spJson;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shopProduct", cascade = CascadeType.ALL)
    @OrderBy(value = "spaRequired desc, spaIdx asc")
    private List<ShopProductAdd> shopProductAddList;

    /** 현재 판매 중인 상품인지 확인*/
    public boolean isSell(){
        if(getSpState() == SpState.Y){
            LocalDateTime now = LocalDateTime.now();

            if(spSellStartDate == null || spSellEndDate == null){
                return true;
            }
            LocalDateTime sellStart = LocalDateTime.of(getSpSellStartDate(), getSpSellStartTime());
            LocalDateTime sellEnd = LocalDateTime.of(getSpSellEndDate(), getSpSellEndTime());
            if(now.isAfter(sellStart) && now.isBefore(sellEnd)){
                return true;
            }
        }
        return false;
    }
    /** 현재 노출 중인 상품인지 확인*/
    public boolean isShow(){
        if(getSpState() == SpState.Y){
            LocalDateTime now = LocalDateTime.now();

            LocalDateTime showStart;
            //null인경우 기간 check 없음
            if(spShowStartDate == null){
                showStart = now.minusDays(1);
            }else{
                showStart = LocalDateTime.of(spShowStartDate, spShowStartTime);
            }
            LocalDateTime showEnd;
            if (spShowEndDate == null) {
                showEnd = now.plusDays(1);
            } else {
                showEnd = LocalDateTime.of(getSpShowEndDate(), getSpShowEndTime());
            }

            if(now.isAfter(showStart) && now.isBefore(showEnd)){
                return true;
            }
        }
        return false;
    }


    /** 대표 썸네일 가져오기*/
    public String getThumbnail(){
        return getShopProductAddList().get(0).getShopProductOptionList().get(0).getGoodsStock().getGsThumbFile1();
    }


    @PrePersist
    @PreUpdate
    public void preSave(){
        if(getShop() == null){
            throw new MsgException("판매SHOP을 선택해주세요.");
        }
        if(getCategory() == null){
            throw new MsgException("필수상품을 선택해주세요.");
        }
    }
}

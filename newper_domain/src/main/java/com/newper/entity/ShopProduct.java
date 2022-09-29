package com.newper.entity;

import com.newper.constant.SpState;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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

    @Enumerated(EnumType.STRING)
    private SpState spState;

    private Integer spMinPrice;
    private Integer spMaxPrice;
    private String spBaseName;
    private Integer spBasePrice;
    private Float spPercent;
    private Integer spQuotaOnce;
    private Integer spQuotaId;
    private String spTag;

    private LocalDate spShowStartDate;
    private LocalTime spShowStartTime;
    private LocalDate spShowEndDate;
    private LocalTime spShowEndTime;
    private LocalDate spSellStartDate;
    private LocalTime spSellStartTime;
    private LocalDate spSellEndDate;
    private LocalTime spSellEndTime;

    /** <pre>
     * key : value
     * scate_idx(전시분류) : scate_idx
     * scate_name(전시분류) : scate_name
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
}

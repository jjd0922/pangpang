package com.newper.entity;

import com.newper.constant.GRank;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 재고 코드*/
@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GoodsStock{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GS_P_IDX", referencedColumnName = "pIdx")
    private Product product ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GS_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec spec ;

    private String gsCode;
    private String gsSale;
    private String gsName;
    private Integer gsPrice;
    @Enumerated(EnumType.STRING)
    private GRank gsRank;
    private String gsMd;
    private Integer gsOriginalPrice;
    private String gsThumbFile1;
    private String gsThumbFile2;
    private String gsThumbFile3;
    private String gsThumbFileName1;
    private String gsThumbFileName2;
    private String gsThumbFileName3;
    private String gsContent;
    private long gsStock;
    private long gsOutStock;
    private long gsMoveStock;
    private Long gsSafeStock;
    private Long gsProperStock;

    private boolean gsDaily;
    private String gsLocation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "goodsStock", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;
    private String gsSabang;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if (!StringUtils.hasText(getGsName())) {
            throw new MsgException("재고상품명을 입력해주세요.");
        }
        if (getProduct() == null) {
            throw new MsgException("상품을 선택해주세요.");
        }
    }

    @Builder.Default
    private Map<String,Object> gsOption = new HashMap<>();

    /**판매 가능한 재고*/
    public long getSellStock(){
        return getGsStock() + getGsMoveStock();
    }


}

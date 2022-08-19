package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class GoodsStock extends BaseEntity {

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
    private Integer gsRank;
    private String gsMd;
    private Integer gsOriginalPrice;
    private String gsThumbFile1;
    private String gsThumbFile2;
    private String gsThumbFile3;
    private String gsThumbFileName1;
    private String gsThumbFileName2;
    private String gsThumbFileName3;
    private String gsContent;
    private Long gsStock;
    private Long gsOutStock;
    private Long gsSafeStock;
    private Long gsProperStock;

    private boolean gsDaily;
    private String gsLocation;

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


}

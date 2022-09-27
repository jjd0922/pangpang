package com.newper.entity;

import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PoProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ppIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_PO_IDX", referencedColumnName = "poIdx")
    private Po po;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_SPEC_IDX", referencedColumnName = "specIdx")
    private Spec spec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PP_SPEC_IDX2", referencedColumnName = "specIdx")
    private Spec spec2;

    private List<Map<String, Object>> ppOption;
    private int ppCost;
    private int ppCount;
    private int ppFixCost;
    private String ppFixMemo;
    private int ppPaintCost;
    private String ppPaintMemo;
    private int ppProcessCost;
    private String ppProcessMemo;
    private String ppMemo;
    private int ppSellPrice;
    private float ppProfitTarget;

    private Map<String, Object> ppJson;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (getPpCost() <= 0) {
            throw new MsgException("구매단가를 입력해주세요");
        } else if (getPpCount() <= 0) {
            throw new MsgException("매입수량을 입력해주세요");
        }
    }
}

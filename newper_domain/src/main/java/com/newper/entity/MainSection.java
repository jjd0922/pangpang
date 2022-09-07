package com.newper.entity;

import com.newper.constant.MsType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MainSection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MS_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;
    /** 메인섹션명*/
    private String msName;
    /** 순서*/
    private int msOrder;
    /**단 (배너 1단/2단)*/
    private int msColumn;
    /** 섹션타입*/
    @Enumerated(EnumType.STRING)
    private MsType msType;
    /** 섹션 정보*/
    private Map<String,Object> msJson = new HashMap<>();

    public void updateMainsectionOrder(int msOrder) {
        if(getMsOrder() < 0){
            setMsOrder(msOrder * -1);
        }else{
            setMsOrder(msOrder);
        }
    }
}

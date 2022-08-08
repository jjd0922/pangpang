package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Popup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer popIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POP_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String PopName;

    private Integer PopDisplay;

    private String PopStart;

    private String PopStartTime;

    private String PopEndTime;

    private String PopMemo;


}

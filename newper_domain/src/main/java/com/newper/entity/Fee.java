package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
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
@Table(name = "COMPANY_FEE")
public class Fee extends CreatedEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cfIdx;

    private char cfType;
    private float cfPercent;
    private int cfMoney;
    private char cfState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CF_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CF_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

}

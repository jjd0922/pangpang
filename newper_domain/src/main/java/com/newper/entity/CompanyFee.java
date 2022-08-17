package com.newper.entity;

import com.newper.constant.CfType;
import com.newper.entity.common.BaseEntity;
import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CompanyFee extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cfIdx;

    @Enumerated(EnumType.STRING)
    private CfType cfType;
    private Float cfPercent;
    private Integer cfMoney;
    private char cfState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CF_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CF_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

}

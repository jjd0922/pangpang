package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {

    @Id
    private Integer pIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "P_CATE_IDX", referencedColumnName = "cateIdx")
    private  Category category;

    private String pCode;
    private Integer pPrice;
    private Integer pNaverPrice;
    private String pType1;
    private String pType2;
    private String pName;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pInfo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pOption;


}

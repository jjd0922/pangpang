package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/*@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
*//**거래처 구분*//*
public class CompanyType {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CT_COM_IDX")
    private Company company;

    @Enumerated(EnumType.STRING)
    private String ctType;

}*/

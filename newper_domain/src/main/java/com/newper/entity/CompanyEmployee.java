package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
/** 거래처 직원-거래처, 거래처 계약관리 양쪽에서 사용하는 테이블(외래키가 상대테이블에 걸려있음) */
public class CompanyEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ceIdx;

    @OneToMany(mappedBy = "companyEmployee")
    private List<Company> company;

    private String ceName;
    private String ceDepart;
    private String cePosition;
    private String ceMail;
    private String cePhone;
    private String ceTel;
    private String ceMemo;

}

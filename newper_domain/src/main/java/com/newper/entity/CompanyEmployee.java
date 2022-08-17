package com.newper.entity;

import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

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
/** 거래처 직원-거래처, 거래처 계약관리 양쪽에서 사용하는 테이블(외래키가 상대테이블에 걸려있음) */
public class CompanyEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ceIdx;

    @OneToMany(mappedBy = "companyEmployee")
    private List<Company> company;

    @OneToMany(mappedBy = "companyEmployee")
    private List<CompanyContract> contract;

    private String ceName;
    private String ceDepart;
    private String cePosition;
    private String ceMail;
    private String cePhone;
    private String ceTel;
    private String ceMemo;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (!StringUtils.hasText(getCeName())) {
            throw new MsgException("거래처담당자 성명을 입력해주세요.");
        } else if (!StringUtils.hasText(getCeMail())) {
            throw new MsgException("거래처담당자 이메일을 입력해주세요.");
        }
    }

    public void ceAllUpdate(CompanyEmployee ce) {
        setCeName(ce.getCeName());
        setCeDepart(ce.getCeDepart());
        setCePosition(ce.getCePosition());
        setCeMail(ce.getCeMail());
        setCePhone(ce.getCePhone());
        setCeTel(ce.getCeTel());
        setCeMemo(ce.getCeMemo());
    }

}

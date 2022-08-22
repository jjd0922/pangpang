package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInsurance extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ciIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CI_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String ciType;
    private String ciInsuranceState;
    private String ciCompany;
    private String ciName;
    private String ciNum;
    private LocalDate ciStartDate;
    private LocalDate ciEndDate;
    private Long ciFee;
    private Float ciPercent;
    private Long ciMoney;
    private String ciFile;
    private String ciFileName;
    private String ciMemo;
    private char ciState;

    @PrePersist
    @PreUpdate
    public void preSave() {
        if (getCiType() == null) {
            throw new MsgException("보증보험구분을 선택해주세요.");
        } else if (getCiInsuranceState() == null) {
            throw new MsgException("보증보험 발행상태를 선택해주세요.");
        } else if (!StringUtils.hasText(getCiCompany())) {
            throw new MsgException("발행사를 입력해주세요.");
        } else if (!StringUtils.hasText(getCiName())) {
            throw new MsgException("증권명을 입력해주세요.");
        } else if (getCiStartDate() == null || getCiEndDate() == null || getCiStartDate().isAfter(getCiEndDate()) || getCiEndDate().isBefore(LocalDate.now())) {
            throw new MsgException("유효한 보증기간을 설정해주세요.");
        } else if (!StringUtils.hasText(getCiNum())) {
            throw new MsgException("증권번호를 입력해주세요.");
        } else if (getCiFee() == null || getCiFee() < 0) {
            throw new MsgException("유효한 보험가입금액을 입력해주세요.");
        } else if (getCiPercent() == null || getCiPercent() < 0) {
            throw new MsgException("유효한 보험요율을 입력해주세요.");
        }
    }

    public void updateInsurance(CompanyInsurance insurance) {
        setCiName(insurance.getCiName());
        setCiEndDate(insurance.getCiEndDate());
        setCiNum(insurance.getCiNum());
        setCiFee(insurance.getCiFee());
        setCiPercent(insurance.getCiPercent());
        setCiMoney(insurance.getCiMoney());
        setCiMemo(insurance.getCiMemo());
    }
}

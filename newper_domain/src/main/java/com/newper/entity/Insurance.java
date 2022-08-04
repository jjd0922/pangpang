package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMPANY_INSURANCE")
public class Insurance extends CreatedEntity {

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
    private String ciMemo;
    private char ciState;

    public void updateInsurance(Insurance insurance) {
        setCiType(insurance.getCiType());
        setCiInsuranceState(insurance.getCiInsuranceState());
        setCompany(insurance.getCompany());
        setCiCompany(insurance.getCiCompany());
        setCiName(insurance.getCiName());
        setCiNum(insurance.getCiNum());
        setCiStartDate(insurance.getCiStartDate());
        setCiEndDate(insurance.getCiEndDate());
        setCiFee(insurance.getCiFee());
        setCiPercent(insurance.getCiPercent());
        setCiMoney(insurance.getCiMoney());
//        setCiFile(insurance.getCiFile());
        setCiMemo(insurance.getCiMemo());
    }
}

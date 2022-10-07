package com.newper.entity;

import com.newper.constant.ComState;
import com.newper.constant.ComType;
import com.newper.entity.common.AddressEmb;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "COMPANY")
@ToString
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comIdx;

    @Enumerated(EnumType.STRING)
    private ComType comType;

    private String comMid;

    @Enumerated(EnumType.STRING)
    private ComState comState;

    private String comName;
    private String comCeo;
    private String comNum;
    private String comNick;
    private String comTel;
    private String comFax;
    private String comBank;
    private String comAccount;

    @Embedded
    private AddressEmb address = AddressEmb.builder().build();

    private String comNumFile;
    private String comNumFileName;
    private String comAccountFile;
    private String comAccountFileName;
    private String comAs;
    private String comAsNum;
    private String comMemo;
    private String comModifiedMemo;

    @Transient
    private String comNumFileOri;
    @Transient
    private String comNumFileNameOri;
    @Transient
    private String comAccountFileOri;
    @Transient
    private String comAccountFileNameOri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COM_U_IDX", referencedColumnName = "uIdx")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="COM_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;

    @OneToMany(mappedBy = "company")
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<CompanyFee> feeList;

    @OneToMany(mappedBy = "company")
    private List<CompanyInsurance> insurances;

    @OneToMany(mappedBy = "company")
    private List<Warehouse> warehouses;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if (getComType() == null) {
            throw new MsgException("사업자 분류를 선택해주세요.");
        } else if (!StringUtils.hasText(getComMid())) {
            throw new MsgException("MID(거래처마스터코드)를 입력해주세요.");
        } else if (getComState() == null) {
            throw new MsgException("거래처 상태를 선택해주세요.");
        } else if (!StringUtils.hasText(getComName())) {
            throw new MsgException("상호법인명을 입력해주세요.");
        } else if (!StringUtils.hasText(getComCeo())) {
            throw new MsgException("대표자명을 입력해주세요.");
        } else if (!StringUtils.hasText(getComNum())) {
            throw new MsgException("사업자번호를 입력해주세요.");
        } else if (!StringUtils.hasText(getComTel())) {
            throw new MsgException("전화번호(회사대표번호)를 입력해주세요.");
        } else if (!StringUtils.hasText(getComBank())) {
            throw new MsgException("은행을 선택해주세요.");
        } else if (!StringUtils.hasText(getComAccount())) {
            throw new MsgException("계좌번호를 입력해주세요.");
        } else if (!StringUtils.hasText(getAddress().getPost())) {
            throw new MsgException("우편번호를 입력해주세요.");
        } else if (!StringUtils.hasText(getAddress().getAddr1())) {
            throw new MsgException("주소(시,도)를 입력해주세요.");
        } else if (!StringUtils.hasText(getAddress().getAddr2())) {
            throw new MsgException("주소(시,군,구)를 입력해주세요.");
        } else if (!StringUtils.hasText(getAddress().getAddr3())) {
            throw new MsgException("도로명(지번)주소를 입력해주세요.");
        } else if (!StringUtils.hasText(getAddress().getAddr4())) {
            throw new MsgException("상세주소를 입력해주세요.");
        }
    }

    public void updateCompany(Company company, AddressEmb address, CompanyEmployee companyEmployee) {
        setComType(company.getComType());
        setComState(company.getComState());
        setComName(company.getComName());
        setComCeo(company.getComCeo());
        setComNum(company.getComNum());
        setComNick(company.getComNick());
        setComTel(company.getComTel());
        setComFax(company.getComFax());
        setComBank(company.getComBank());
        setComAccount(company.getComAccount());
        setAddress(address);
        setComAs(company.getComAs());
        setComAsNum(company.getComAsNum());
        setComModifiedMemo(company.getComModifiedMemo());
        setCompanyEmployee(companyEmployee);
    }
}

package com.newper.entity;

import com.newper.constant.ComState;
import com.newper.constant.ComType;
import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
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
    private Address address = Address.builder().build();

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
    @JoinColumn(name="COM_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;

/*    @OneToMany(mappedBy = "company")
    private List<CompanyType> companyType;*/


    @OneToMany(mappedBy = "company")
    private List<User> users;

    @OneToMany(mappedBy = "company")
    private List<Fee> feeList;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

    @OneToMany(mappedBy = "company")
    private List<Insurance> insurances;

    public void updateCompany(Company company, Address address, CompanyEmployee companyEmployee) {
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

package com.newper.entity;

import com.newper.constant.ComState;
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
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comIdx;

    /*@Enumerated(EnumType.STRING)*/
    private String comType;

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
    private Address address;

    private String comNumFile;
    private String comAccountFile;
    private String comAs;
    private String comAsNum;
    private String comMemo;
    private String comModifiedMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="COM_CE_IDX", referencedColumnName = "ceIdx")
    private CompanyEmployee companyEmployee;

/*    @OneToMany(mappedBy = "company")
    private List<CompanyType> companyType;*/



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;
}

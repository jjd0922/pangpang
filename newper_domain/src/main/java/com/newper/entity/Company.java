package com.newper.entity;

import com.newper.constant.ComState;
import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "COMPANY")
@ToString
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
    private Address address = Address.builder().build();

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


    @OneToMany(mappedBy = "company")
    private List<User> users;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

    public void companyAllUpdate(Map<String, Object> map, Address address) {

        setComType(map.get("comType").toString());
        setComMid(map.get("comMid").toString());
//        setComState((ComState) map.get("comState"));
        setComName(map.get("comName").toString());
        setComCeo(map.get("comCeo").toString());
        setComNum(map.get("comNum").toString());
        setComNick(map.get("comNick").toString());
        setComTel(map.get("comTel").toString());
        setComFax(map.get("comFax").toString());
        setComBank(map.get("comBank").toString());
        setComAccount(map.get("comAccount").toString());
        setAddress(address);
//        setComNumFile(map.get("comNumFile").toString());
//        setComAccountFile(map.get("comAccountFile").toString());
        setComAs(map.get("comAs").toString());
        setComAsNum(map.get("comAsNum").toString());
        setComModifiedMemo(map.get("comModifiedMemo").toString());
        // companyemployee 같이?
    }
}

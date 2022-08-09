package com.newper.entity;

import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "USER")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "U_IDX")
    private Integer uIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "U_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "U_AUTH_IDX", referencedColumnName = "authIdx")
    private Auth auth;

    private String uName;
    private String uTel;
    private String uPhone;
    private LocalDate uBirth;

    private String uType;
    private String uDepart;
    private String uPosition;
    private String uJob;
    private String uCompanyMail;
    private String uPersonMail;
    private String uCompanyTel;
    private String uState;
    private String uId;
    private String uPassword;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user")
    private List<Company> companies;

    /*생년월일*/
    public String getUBirthStr(){
        return getUBirth().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

}



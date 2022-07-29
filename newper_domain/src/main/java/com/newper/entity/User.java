package com.newper.entity;

import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;

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
    private Integer uIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "U_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

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

    public User(Integer uIdx, Company company) {
        this.uIdx = uIdx;
        this.company = company;
    }
}



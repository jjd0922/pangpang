package com.newper.entity;

import com.newper.constant.UType;
import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Enumerated(EnumType.STRING)
    private UType uType;

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

    @OneToMany(mappedBy = "user")
    private List<CompanyContract> contracts;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if (!StringUtils.hasText(getUName())) {
            throw new MsgException("이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(getUPhone())) {
            throw new MsgException("휴대폰번호를 입력해주세요.");
        }
//        String comName = paramMap.getString("COM_NAME");
//        if (comName == null || comName.equals("")) {
//            throw new MsgException("상호법인명을 입력해주세요.");
//        }
//
//        String uState = paramMap.getString("U_STATE");
//        if (uState == null || uState.equals("")) {
//            throw new MsgException("상태를 선택해주세요.");
//        }
//        String uId = paramMap.getString("U_ID");
//        if (uId == null || uId.equals("")) {
//            throw new MsgException("로그인 ID를 입력해주세요.");
//        }
//        String uPassword = paramMap.getString("U_PASSWORD");
//        if (uPassword == null || uPassword.equals("")) {
//            throw new MsgException("비밀번호를 입력해주세요.");
//        }
//        String authIdx = paramMap.getString("U_AUTH_IDX");
//        if (authIdx == null || authIdx.equals("")) {
//            throw new MsgException("권한을 입력해주세요.");
//        }
//
//        String uBirth = paramMap.getString("U_BIRTH");
//        if ( uBirth.equals("")) {
//            uBirth=null;
//            paramMap.put("U_BIRTH",uBirth);
//        }
//

    }

    /*생년월일*/
    public String getUBirthStr(){
        return getUBirth().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

}



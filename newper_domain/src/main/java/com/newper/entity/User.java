package com.newper.entity;

import com.newper.constant.UType;
import com.newper.entity.common.Address;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

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

   @ColumnTransformer(write = "SHA2(?,512)")
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
        if (this.uState == null ) {
            throw new MsgException("상태를 선택해주세요.");
        }

        if (uId == null){
            throw new MsgException("로그인 ID를 입력해주세요.");
        }
        //영어 숫자 아닌 문자 있는지 체크
        uId=uId.trim();
        if (!Pattern.matches("^[a-zA-Z0-9]*$", uId)) {
            throw new MsgException("ID는 영어, 숫자로만 가능합니다");
        }
        if (uId.equals("")) {
            throw new MsgException("로그인 ID를 입력해주세요.");
        }
        if (uPassword == null || uPassword.equals("")) {
            throw new MsgException("비밀번호를 입력해주세요.");
        }

    }

    /*생년월일*/
    public String getUBirthStr(){
        return getUBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /** 랜덤 문자열 생성 (숫자,특수문자,대소문자)*/
    public void setRandomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            // idx = (int) (len * Math.random());
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }

        setUPassword(sb.toString());
    }

    /**비밀번호 암호화*/
    public static String parseSHA(String str){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.reset();
            digest.update(str.getBytes(StandardCharsets.UTF_8));
            String result = String.format("%0128x", new BigInteger(1, digest.digest()));
            return result;
        }catch (NoSuchAlgorithmException nae){
            throw new MsgException("해쉬 암호화 중 에러발생");
        }
    }


/**사용자 업데이트*/

    public void updateUser(int uIdx){
        if(getUIdx() < 0){
            setUIdx(uIdx * -1);
        }else{
            setUIdx(uIdx);
        }
    }



}



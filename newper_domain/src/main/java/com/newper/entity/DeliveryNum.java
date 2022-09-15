package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeliveryNum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dnIdx;
    private String dnState;
    private String dnNum;
    private String dnCompany;
    private Date dnDate;
    private LocalDate createdDate;

    /** 랜덤 문자열 생성 (숫자,특수문자,대소문자)*/
    public void setRandomInvoice(int size) {
        char[] charSet = new char[]
                {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',};
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

        setDnNum(sb.toString());
        setDnIdx(dnIdx);
    }


}

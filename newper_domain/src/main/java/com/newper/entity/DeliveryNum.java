package com.newper.entity;

import com.newper.constant.DnSender;
import com.newper.constant.DnState;
import com.newper.constant.DnType;
import com.newper.constant.OgdnType;
import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DeliveryNum extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DN_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @Enumerated(EnumType.STRING)
    private DnType dnType;

    @Enumerated(EnumType.STRING)
    private DnState dnState;

    @Enumerated(EnumType.STRING)
    private DnSender dnSender;

    private String dnNum;
    private LocalDate dnDate;
    private LocalDate dnRelease;
    private LocalDate dnSchedule;

    @Builder.Default
    private Map<String,Object> dnJson = new HashMap<String, Object>();

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

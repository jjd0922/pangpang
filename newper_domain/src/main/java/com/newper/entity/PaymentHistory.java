package com.newper.entity;

import com.newper.constant.PhType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PH_PAY_IDX", referencedColumnName = "payIdx")
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private PhType phType;

    private boolean phFlag;
    private String phReq;
    private String phRes;
    private LocalDate phReqDate;
    private LocalTime phReqTime;
    private String phCode;


    /** 결제 고유 idx*/
    public String getMerchantId(){
        return getPhIdx()+"";
    }

    /** phReq세팅시 날짜시간도 세팅*/
    public void setPhReq(String phReq) {
        LocalDateTime now = LocalDateTime.now();
        setPhReqDate(now.toLocalDate());
        setPhReqTime(now.toLocalTime());
        this.phReq = phReq;
    }
}

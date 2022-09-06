package com.newper.entity;

import com.newper.constant.PhResult;
import com.newper.constant.PhType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@DynamicInsert
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PhResult phResult = PhResult.WAIT;

    /** phReq세팅시 날짜시간도 세팅*/
    public void setPhReq(String phReq) {
        LocalDateTime now = LocalDateTime.now();
        setPhReqDate(now.toLocalDate());
        setPhReqTime(now.toLocalTime());
        this.phReq = phReq;
    }
}

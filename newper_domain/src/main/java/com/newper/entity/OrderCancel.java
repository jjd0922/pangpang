package com.newper.entity;

import com.newper.constant.OCancelState;
import com.newper.constant.OcRefundState;
import com.newper.constant.OcState;
import com.newper.constant.OcType;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderCancel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ocIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OC_O_IDX", referencedColumnName = "oIdx")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OC_OA_IDX", referencedColumnName = "oaIdx")
    private OrderAddress orderAddress;

    private LocalDate ocRequestDate = LocalDate.now();
    private LocalTime ocRequestTime = LocalTime.now();

    private OcState ocState;
    private String ocReason;
    private OcType ocType;
    private String ocContent;
    private OcRefundState ocRefundState;
    private String ocRefundNum;
    private String ocRefundBank;
    private int ocRefundMoney;
    private int ocRefundPoint;
    private int ocRefundMileage;
    private int ocRefundPlusMileage;
    private LocalDate ocRefundDate;
    private Map<String, Object> ocJson;


}

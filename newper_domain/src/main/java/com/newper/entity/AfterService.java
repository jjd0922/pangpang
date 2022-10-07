package com.newper.entity;

import com.newper.constant.AsState;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "after_service")
@Builder
public class AfterService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long asIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_OC_IDX", referencedColumnName = "ocIdx")
    private OrderCancel orderCancel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_DN_IDX2", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum2;

    @Enumerated(EnumType.STRING)
    private AsState asState;

    private String asType;
    private String asName;
    private String asMail;
    private String asPhone;
    private String asMemo;
    private LocalDate asDate;
    private LocalTime asTime;

    private String asMoneyState;

    private int asCost;
    private int asReqMoney;
    private int asRcvMoney;
    private int asFinalCost;


    @Builder.Default
    private Map<String,Object> asFile = new HashMap<String, Object>();


    /**<pre>
     * {
     * asReason : "",
     * asCost :[1000, 2000],
     * asDate:[yyyy-MM-dd,yyyy-MM-dd]
     * }
     * </pre> */
    @Builder.Default
    private Map<String, Object> asJson = new HashMap<>();


}

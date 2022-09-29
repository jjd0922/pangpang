package com.newper.entity;

import com.newper.constant.OCancelState;
import com.newper.constant.ODeliveryState;
import com.newper.constant.OLocation;
import com.newper.constant.OState;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_PAY_IDX", referencedColumnName = "payIdx")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "O_AD_IDX", referencedColumnName = "adIdx")
    private OrderAddress orderAddress;

    @Enumerated(EnumType.STRING)
    private OLocation oLocation;

    /** 주문코드 oDate+oTime+oIdx */
    private String oCode;
    /**임시여부. false인 경우 고객에게 노출X*/
    private boolean oTemp;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OState oState = OState.BEFORE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ODeliveryState oDeliveryState = ODeliveryState.BEFORE;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OCancelState oCancelState = OCancelState.NONE;

    private String oName;
    private String oTel;
    private String oPhone;

    @Column(updatable = false)
    private LocalDate oDate;
    @Column(updatable = false)
    private LocalTime oTime;

    private String oMemo;

    /**<pre>
     * spo : { spoIdx_spoIdx:cnt ...}
     * </pre>*/
    @Builder.Default
    private Map<String,Object> oJson = new HashMap<>();

    @OneToMany(mappedBy = "ogIdx", cascade = CascadeType.ALL)
    private List<OrderGs> orderGs;

    @PrePersist
    @PreUpdate
    public void ordersSave(){
        if (getODate() == null) {
            throw new MsgException("주문완료일을 입력해주세요.");
        }
        if (getShop() == null) {
            throw new MsgException("주문분양몰을 선택해주세요.");
        }
        if (!StringUtils.hasText(getOName())) {
            throw new MsgException("주문자 이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(getOPhone())) {
            throw new MsgException("주문자 연락처를 입력해주세요.");
        }
        if (getOLocation() == null){
            throw new MsgException("주문경로를 입력해주세요.");
        }

        setOPhone((getOPhone()+"").replaceAll("[^0-9]", ""));
        setOTel((getOTel()+"").replaceAll("[^0-9]", ""));

    }
    @PostPersist
    public void ordersCode(){
        //주문코드 o_code update
        setOCode(oDate.format(DateTimeFormatter.ofPattern("yyMMdd")) + oTime.format(DateTimeFormatter.ofPattern("HHmm")) + getOIdx());
    }

    /** 결제시 사용할 주문 제목 가져오기*/
    public String getOrderPaymentTitle(){
        List<OrderGs> orderGsList = getOrderGs();
        return orderGsList.get(0).getShopProductOption().getSpoName()+" 외 "+ (orderGsList.size()-1)+"건";
    }

    public void setPayment(Payment payment){
        this.payment = payment;
        payment.setOrders(this);
    }
}

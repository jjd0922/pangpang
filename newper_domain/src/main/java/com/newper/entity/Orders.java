package com.newper.entity;

import com.newper.constant.*;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private boolean oReal;

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
     * title : ogg_spo 이름 외 몇건 (주문명)
     * </pre>*/
    @Builder.Default
    private Map<String,Object> oJson = new HashMap<>();

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderGsGroup> orderGsGroupList;
    @OneToMany(fetch = FetchType.LAZY, mappedBy="orders", cascade= CascadeType.ALL)
    private List<Review> reviews;

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

    /** oJson.get(key). null일 경우 "" 빈문자열 return*/
    public Object getOJsonValue(String oJsonKey){
        if (getOJson().containsKey(oJsonKey)) {
            return getOJson().get(oJsonKey);
        }else{
            return "";
        }
    }

    public void setPayment(Payment payment){
        this.payment = payment;
        payment.setOrders(this);
    }
}

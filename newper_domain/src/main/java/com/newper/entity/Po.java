package com.newper.entity;


import com.newper.constant.*;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Po extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer poIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_COM_IDX", referencedColumnName = "comIdx")
    private Company company; //매입처

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_SELL_COM_IDX", referencedColumnName = "comIdx")
    private Company company_sell; //판매처

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_WH_IDX", referencedColumnName = "whIdx")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_CC_IDX", referencedColumnName = "ccIdx")
    private CompanyContract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_HW_IDX", referencedColumnName = "hwIdx")
    private Hiworks hiworks;

    /** one to one이지만 lazy fetch때문에 one to many로 설정함*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "po")
    private List<InGroup> inGroup;

    @Enumerated(EnumType.STRING)
    private PoState poState;
    @Enumerated(EnumType.STRING)
    private PoType poType;
    /** 발주완료일. 매입처로 보낸 날짜*/
    private LocalDate poRequestDate;
    private String poMemo;
    private boolean poRepurchase;
    private long poTotalAmount;
    private long poTotalCount;
    private String poBuyChannel;
    private long poBuyReceiveAmount;
    private long poBuyUnpaidAmount;
    @Enumerated(EnumType.STRING)
    private PType1 poBuyProductType;
    private String poBuyOriUse;
    private String poBuySellPeriod;
    @Enumerated(EnumType.STRING)
    private Channel poSellChannel;
    private long poSellReceiveAmount;
    private long poSellUnpaidAmount;
    private LocalDate poSellPayDate;
    private long poSellTotalAmount;
    private Float poSellProfit;
    private String poSellUse;
    private LocalDate poInDate;
    private LocalDate poDueDate;
    private LocalDate poRefundDate;
    private LocalDate poAsDate;
    @Enumerated(EnumType.STRING)
    private PoDeliveryMain poDeliveryMain;
    private int poDeliveryCost;
    private LocalDate poTaxMonth;
    private LocalDate poPayDate;
    private String poPayAccount;
    private String poFile;
    private String poFileName;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "po", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if (getCompany() == null) {
            throw new MsgException("매입처를 선택해 주세요");
        } else if (getCompany_sell() == null) {
            throw new MsgException("판매처를 선택해 주세요");
        } else if (getWarehouse() == null) {
            throw new MsgException("입고예정창고를 선택해주세요");
        } else if (getPoType() == null) {
            throw new MsgException("긴급발주 여부를 선택해주세요");
        } else if (getPoBuyProductType() == null) {
            throw new MsgException("제품상태(품목구분1)을 선택해주세요");
        } else if (getPoSellChannel() == null) {
            throw new MsgException("판매채널유형을 선택해주세요");
        } else if (getPoInDate().isBefore(LocalDate.now())) {
            throw new MsgException("유효한 입고예정일을 입력해주세요");
        } else if (getPoDueDate().isBefore(LocalDate.now())) {
            throw new MsgException("유효한 납기일을 입력해주세요");
        } else if (getPoPayDate().isBefore(LocalDate.now())) {
            throw new MsgException("유효한 지급예정일을 입력해주세요");
        } else if (!StringUtils.hasText(getPoPayAccount())) {
            throw new MsgException("입금계좌정보를 입력해주세요");
        } else if (getPoDeliveryCost() < 0) {
            throw new MsgException("유효한 매입운송비를 입력해주세요");
        } else if ((getPoDeliveryMain() == PoDeliveryMain.MAIN && getPoDeliveryCost() == 0)) {
            throw new MsgException("유효한 매입운송비를 입력해주세요");
        }
    }

    public void updateAll(Po po) {
        setPoType(po.getPoType());
        setPoMemo(po.getPoMemo());
        setPoRepurchase(po.poRepurchase);
        setPoTotalAmount(po.getPoTotalAmount());
        setPoTotalCount(po.getPoTotalCount());
        setPoBuyChannel(po.getPoBuyChannel());
        setPoBuyReceiveAmount(po.getPoBuyReceiveAmount());
        setPoBuyUnpaidAmount(po.getPoBuyUnpaidAmount());
        setPoBuyProductType(po.getPoBuyProductType());
        setPoBuyOriUse(po.getPoBuyOriUse());
        setPoBuySellPeriod(po.getPoBuySellPeriod());
        setPoSellChannel(po.getPoSellChannel());
        setPoSellReceiveAmount(po.getPoSellReceiveAmount());
        setPoSellUnpaidAmount(po.getPoSellUnpaidAmount());
        setPoSellPayDate(po.getPoSellPayDate());
        setPoSellProfit(po.getPoSellProfit());
        setPoSellUse(po.getPoSellUse());
        setPoSellTotalAmount(po.getPoSellTotalAmount());
        setPoInDate(po.getPoInDate());
        setPoDueDate(po.getPoDueDate());
        setPoRefundDate(po.getPoRefundDate());
        setPoAsDate(po.getPoAsDate());
        setPoDeliveryMain(po.getPoDeliveryMain());
        setPoDeliveryCost(po.getPoDeliveryCost());
        setPoTaxMonth(po.getPoTaxMonth());
        setPoPayDate(po.getPoPayDate());
        setPoPayAccount(po.getPoPayAccount());
    }

}

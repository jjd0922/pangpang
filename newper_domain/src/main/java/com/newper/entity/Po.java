package com.newper.entity;


import com.newper.constant.Channel;
import com.newper.constant.PType1;
import com.newper.constant.PoState;
import com.newper.constant.PoType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

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
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_SELL_COM_IDX", referencedColumnName = "comIdx")
    private Company company_sell;

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
    private float poSellProfit;
    private String poSellUse;
    private LocalDate poInDate;
    private LocalDate poDueDate;
    private LocalDate poRefundDate;
    private LocalDate poAsDate;
    private String poDeliveryMain;
    private int poDeliveryCost;
    private LocalDate poTaxMonth;
    private LocalDate poPayDate;
    private String poPayAccount;
    private String poFile;
    private String poFileName;
    private String poCode;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "po", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;

}

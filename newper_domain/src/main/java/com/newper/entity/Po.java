package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.util.Date;

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

    private String poState;
    private String poType;
    private String poRequestDate;
    private String poMemo;
    private String poRepurchase;
    private long poTotalAmount;
    private long poTotalCount;
    private String poBuyChannel;
    private long poBuyReceiveAmount;
    private long poUnpaidAmount;
    private String poBuyProductType;
    private String poBuyOriUse;
    private String poBuyProductState;
    private String poBuySellPeriod;
    private String poSellChannel;
    private long poSellReceiveAmount;
    private long poSellUnpaidAmount;
    private long poSellTotalAmount;
    private String poSellPayDate;
    private float poSellProfit;
    private String poSellUse;
    private String poInDate;
    private String poDueDate;
    private String poRefundDate;
    private String poAsDate;
    private String poDeliveryMain;
    private String poDeliveryCost;
    private String poTaxMonth;
    private String poPayDate;
    private String poPayAccount;
    private String poFile;
    private String poFileName;

}

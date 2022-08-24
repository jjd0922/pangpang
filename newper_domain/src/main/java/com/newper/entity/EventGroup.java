package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class EventGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long egIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EG_PG_MSO_IDX", referencedColumnName = "msoIdx")
    private MainSectionOrder mainSectionOrder;


    private String egPgName;

    private String egThumbNail;

    private String egTitle;

    private String egContent;

    private Date egStartDate;

    private Date egEndDate;

    private Date egOpenDate;

    private Time egOpenTime;

    private Date egCloseDate;

    private Time egCloseTime;

    private String egDesign;

    private String egDesignMobile;

    private String egImage;

    private String egImageMobile;

    private Integer egTopDisplay;

    private String egMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EG_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;


}

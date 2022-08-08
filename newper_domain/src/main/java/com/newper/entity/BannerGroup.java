package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BannerGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BG_MSO_IDX", referencedColumnName = "msoIdx")
    private  MainSectionOrder mainSectionOrder;

    private String bgTitle;

    private String bgDesign;

    private String bgDesignPage;

    private String bgDesignArrow;

    private int bgHeightWeb;

    private int bgHeightMobile;

    private String bgMemo;

    private int bgShopIdx;







}

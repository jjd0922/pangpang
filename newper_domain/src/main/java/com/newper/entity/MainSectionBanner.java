package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MainSectionBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msbnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MSBN_MS_IDX", referencedColumnName = "msIdx")
    private MainSection mainSection;

    /** 순서*/
    private int msbnOrder;
    /** 배너이미지 (WEB)*/
    private String msbnImage;
    /** 배너이미지 (MOBILE)*/
    private String msbnImageMobile;
    /** 링크*/
    private String msbnUrl;
}

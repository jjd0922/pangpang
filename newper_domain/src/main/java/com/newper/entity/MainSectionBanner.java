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
    private String msbnWebFile;
    /** 배너이미지 이름(WEB)*/
    private String msbnWebFileName;
    /** 배너이미지 (MOBILE)*/
    private String msbnMobileFile;
    /** 배너이미지 이름(MOBILE)*/
    private String msbnMobileFileName;
    /** 링크*/
    private String msbnUrl;
}

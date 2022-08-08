package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.awt.image.renderable.RenderableImage;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder

public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BN_BG_IDX", referencedColumnName = "bgIdx")
    private  BannerGroup bannerGroup;

    private int bnDisplay;

    private String bnImage;

    private String bnImageMobile;

    private String bnUrl;

}

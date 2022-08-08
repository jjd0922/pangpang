package com.newper.entity;

import com.newper.entity.common.BaseEntity;
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
public class FloatingBar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fbIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FB_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String fbName;

    private Integer fbDisplay;

    private String fbType;

    private String fbUrl;

    private String fbThumbNail;

    private String fbThumbNailMobile;


}

package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.security.PrivateKey;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Shop extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopIdx;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "SHOP_PI_IDX", referencedColumnName = "piIdx")
//    private PayInfo payInfo;
//
//    private String shopState;

    private String shopName;

//    private String shopType;
//
//    private String shopPoint;
//
    private Float shopMileage;

    private String shopBasket;
//
//    private String shopHdMeta;
//
//    private String shopHdLoginGroup;
//
//    private String shopDesign;






}

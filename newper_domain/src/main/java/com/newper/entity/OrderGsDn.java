package com.newper.entity;

import com.newper.constant.OgdnType;
import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderGsDn extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ogdnIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OGDN_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OGDN_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;

    @Enumerated(EnumType.STRING)
    private OgdnType ogdnType;



}

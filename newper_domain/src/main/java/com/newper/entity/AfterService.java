package com.newper.entity;

import com.newper.constant.AsState;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "after_service")
@Builder
public class AfterService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long asIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;

    @Enumerated(EnumType.STRING)
    private AsState asState;
    private String asName;
    private String asMail;
    private String asPhone;
    private String asMemo;
//    private String asFile;
    private LocalDate asDate;
    private LocalTime asTime;


    @Builder.Default
    private Map<String,Object> asFile = new HashMap<String, Object>();


//
//    @Transient
//    private String asFileOri;
//    @Transient
//    private String asFileNameOri;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;


}

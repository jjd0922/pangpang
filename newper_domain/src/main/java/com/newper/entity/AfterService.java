package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    private String asState;
    private String asName;
    private String asMail;
    private String asPhone;
    private String asMemo;
    private String asFile;
    private String asFileName;
    private LocalDate asDate;
    private LocalTime asTime;



    @Transient
    private String asFileOri;
    @Transient
    private String asFileNameOri;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AS_DN_IDX", referencedColumnName = "dnIdx")
    private DeliveryNum deliveryNum;


}

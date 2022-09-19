package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ResellGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RG_RS_IDX", referencedColumnName = "rsIdx")
    private Resell resell;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RG_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;

    private String rgMemo;
    private String rgState;
}

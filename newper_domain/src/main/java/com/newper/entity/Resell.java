package com.newper.entity;

import com.newper.constant.GRank;
import com.newper.constant.GState;
import com.newper.constant.GStockState;
import com.newper.entity.common.BaseEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Resell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RS_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String rsMemo;

    private LocalDate rsDate = LocalDate.now();
    private LocalTime rsTime = LocalTime.now();
}

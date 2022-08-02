package com.newper.entity;

import com.newper.converter.ConvertList;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "po_estimate")
public class Estimate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer peIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PE_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String peCode;
    private String peState;
    private String peName;
    private String pePeriodStart;
    private String pePeriodEnd;
    private String peEstimateCount;
    private String peEstimateCost;
    private String peEstimateFile;
    private String peMemo;
    private String peReason;
}



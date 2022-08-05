package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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
    private String peStart;
    private String peEnd;
    private String peCount;
    private String peCost;
    private String peFile;
    private String peFileName;
    private String peMemo;

    @Transient
    private String peFileOri;
    @Transient
    private String peFileNameOri;

}



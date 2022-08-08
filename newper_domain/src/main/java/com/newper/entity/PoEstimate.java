package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PoEstimate extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer peIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PE_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String peCode;

    private String peState;

    private String peName;

    private Date peStart;

    private Date peEnd;

    private Integer peCount;

    private Long peCost;

    private String peFile;

    private String peFileName;

    private String peMemo;
}

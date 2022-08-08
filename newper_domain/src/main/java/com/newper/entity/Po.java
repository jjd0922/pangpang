package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Po extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer poIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_WH_IDX", referencedColumnName = "whIdx")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PO_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String poString;

    private String poType;

    private Date poRequestDate;

    private Date poDate;

    private String poMemo;

    private String poRepurchase;

    private String poWarehousingState;

    private Integer poTotalAmount;

    private Integer poTotalCount;



}

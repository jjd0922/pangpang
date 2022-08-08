package com.newper.entity;

import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.lang.invoke.SwitchPoint;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProcessGroup extends CreatedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pgIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PG_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String pgType;

    private String pgLastState;

    private Date pgRequestDate;

    private String pgReqMemo;

    private String pgCheckMemo;

    private String pgDoneMemo;

    private Date pgStartDate;

    private Date pgEndDate;

    private Integer pgUIdx;

}

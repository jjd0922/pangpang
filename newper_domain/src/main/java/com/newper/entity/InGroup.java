package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class InGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer igIdx;

    private Date igReceiveDate;

    private Time igReceiveTime;

    private String igState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IG_WH_IDX", referencedColumnName = "whIdx")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IG_U_IDX", referencedColumnName = "uIdx")
    private User user;

    private Date igDate;

    private Time igTime;

    private String igMemo;

    private String igDoneMemo;


}

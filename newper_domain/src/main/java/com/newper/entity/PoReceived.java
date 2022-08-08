package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.nio.channels.Pipe;
import java.security.PrivateKey;
import java.sql.Time;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PoReceived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prIdx;

    private Integer prCount;

    private String prMemo;

    private Date prDate;

    private Time prTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PR_P_IDX", referencedColumnName = "pIdx")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PR_PO_IDX", referencedColumnName = "poIdx")
    private Po po;
}

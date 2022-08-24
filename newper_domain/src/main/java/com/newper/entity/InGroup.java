package com.newper.entity;

import com.newper.constant.IgState;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@DynamicInsert
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IG_PO_IDX", referencedColumnName = "poIdx")
    private Po po;

    private LocalDate igReceiveDate;

    private LocalTime igReceiveTime;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private IgState igState = IgState.NONE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IG_WH_IDX", referencedColumnName = "whIdx")
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IG_U_IDX", referencedColumnName = "uIdx")
    private User user;

    private LocalDate igDate;

    private LocalTime igTime;

    private String igMemo;

    private String igDoneMemo;


    @OneToMany(mappedBy = "inGroup", cascade = CascadeType.ALL)
    private List<InProduct> inProductList;

}

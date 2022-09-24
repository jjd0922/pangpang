package com.newper.entity;

import com.newper.constant.CgState;
import com.newper.constant.CgType;
import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CheckGroup extends CreatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cgIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_CHECK_IDX", referencedColumnName = "checkIdx")
    private Checks checks;

    @Enumerated(EnumType.STRING)
    private CgState cgState;

    private LocalDate cgRequestDate;

    private String cgReqMemo;

    private String cgCheckMemo;

    private String cgDoneMemo;

    private LocalDate cgStartDate;

    private LocalDate cgEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_U_IDX", referencedColumnName = "uIdx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CG_U_IDX2", referencedColumnName = "uIdx")
    private User user2;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CgType cgType = CgType.IN;


}

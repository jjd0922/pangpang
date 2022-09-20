package com.newper.entity;

import com.newper.constant.HwState;
import com.newper.constant.HwType;
import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "hiworks")
public class Hiworks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hwIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HW_U_IDX", referencedColumnName = "uIdx")
    private User user;

    @Enumerated(EnumType.STRING)
    private HwType hwType;
    @Enumerated(EnumType.STRING)
    private HwState hwState;
    private LocalDate hwReqDate;
    private LocalTime hwReqTime;
    private LocalDate hwAprvDate;
    private LocalTime hwAprvTime;
    private String hwAprvId;
    private String hwMemo;
}

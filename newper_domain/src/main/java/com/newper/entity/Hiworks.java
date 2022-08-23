package com.newper.entity;

import com.newper.constant.HwState;
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
@Table(name = "hiworks")
public class Hiworks extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hwIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HW_U_IDX", referencedColumnName = "uIdx")
    private User user;

    private String hwType;
    @Enumerated(EnumType.STRING)
    private HwState hwState;
    private String hwReqDate;
    private String hwReqTime;
    private String hwAprvId;
    private String hwAprvDate;
    private String hwAprvTime;
}

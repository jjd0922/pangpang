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
@Table(name = "hiworks_history")
public class HiworksHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hwhIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HWH_HW_IDX", referencedColumnName = "hwIdx")
    private Hiworks hiworks;

    private String hwhType;
    private String hwh_req;
    private String hwh_res;
    private boolean hwh_result;
}

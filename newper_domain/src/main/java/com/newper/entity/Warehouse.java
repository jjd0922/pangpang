package com.newper.entity;


import com.newper.constant.WhState;
import com.newper.entity.common.Address;
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
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer whIdx;

    @Enumerated(EnumType.STRING)
    private WhState whState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String whName;

    @Embedded
    private Address address;

}
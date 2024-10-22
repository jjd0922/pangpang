package com.pangpang.entity;

import com.pangpang.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CheckList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CL_BD_IDX", referencedColumnName = "bdIdx")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CL_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private String clContent;
    private String clDisplay;

}

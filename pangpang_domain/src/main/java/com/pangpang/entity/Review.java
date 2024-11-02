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
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "R_BD_IDX", referencedColumnName = "bdIdx")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "R_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private String rGoodContent;
    private String rBadContent;
    private boolean rDisplay;

}

package com.pangpang.entity;

import com.pangpang.entity.common.BaseEntity;
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
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bdIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BD_CATE_IDX", referencedColumnName = "cateIdx")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BD_CU_IDX", referencedColumnName = "cuIdx")
    private Customer customer;

    private String bdState;
    private int bdRate;
    private String bdTitle;
    private String bdContent;
    private int bdCnt;
    private String bdImg;
    private String bdMap;
    private String bdLink;
    private LocalDate bdStartDate;
    private LocalDate bdEndDate;
    private boolean bdDisplay;
}

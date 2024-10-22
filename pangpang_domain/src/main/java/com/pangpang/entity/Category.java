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
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cateIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATE_PARENT_IDX", referencedColumnName = "cateIdx")
    private Category category;

    private String cateType;
    private String cateDepth;
    private String cateName;

}

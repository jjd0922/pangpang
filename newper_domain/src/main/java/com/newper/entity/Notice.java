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
public class Notice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ntIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="NT_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    @Builder.Default
    private char ntTop = 'N';
    private String ntTitle;
    private String ntContent;
    private Integer ntDisplay;

}

package com.newper.entity;

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
public class Sabang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_O_IDX", referencedColumnName = "oIdx")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;

    private String saMallProductId;
}

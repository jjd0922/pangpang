package com.newper.entity;

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
public class EventCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ecIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EC_EG_IDX", referencedColumnName = "egIdx")
    private EventGroup eventGroup;

    /** 이벤트 카테고리명*/
    private String ecTitle;
    /** 순서*/
    private int ecOrder;

}

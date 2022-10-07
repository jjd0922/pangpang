package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    /** 이벤트 그룹 카테고리 상품 */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventCategory", cascade = CascadeType.ALL)
    @OrderBy(value = "espOrder asc")
    @Builder.Default
    private List<EventSp> eventSpList = new ArrayList<>();

    /** 이벤트 카테고리명*/
    private String ecTitle;
    /** 순서*/
    private int ecOrder;

}

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
public class EventSp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long espIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESP_EC_IDX", referencedColumnName = "ecIdx")
    private EventCategory eventCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESP_SP_IDX", referencedColumnName = "spIdx")
    private ShopProduct shopProduct;

    private int espOrder;
}

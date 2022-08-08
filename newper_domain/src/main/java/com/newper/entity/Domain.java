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
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer domIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DOM_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private String domUrl;



}

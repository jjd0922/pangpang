package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MainSection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MS_SHOP_IDX", referencedColumnName = "shopIdx")
    private  Shop shop;

    private String msName;

    private int msDislay;

    private  int msDan;






}

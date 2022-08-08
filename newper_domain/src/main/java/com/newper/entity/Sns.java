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
public class Sns  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snsIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="SNS_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    private Integer snsOrder;

    private String snsTitle;

    private String snsUrl;

    private String snsThumbnail;


}

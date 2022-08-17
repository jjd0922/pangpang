package com.newper.entity;

import com.newper.constant.GiftState;
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
public class Gift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftIdx;

    @Enumerated(EnumType.STRING)
    private GiftState giftState;

    private String giftCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GIFT_GIFTG_IDX", referencedColumnName = "giftgIdx")
    private GiftGroup giftGroup;
}

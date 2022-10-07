package com.newper.entity;

import com.newper.constant.CcgAdjust;
import com.newper.constant.OgCalCloseState;
import com.newper.constant.OgCalConfirmState;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderGsGroupCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oggcIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OGGC_OC_IDX", referencedColumnName = "ocIdx")
    private OrderCancel orderCancel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OGGC_SPO_IDX", referencedColumnName = "spoIdx")
    private ShopProductOption shopProductOption;

    private int oggcCnt;
}

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

package com.newper.entity;

import com.newper.entity.common.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CalculateAdjust extends BaseEntity {
    /** 조정금액 그룹 */
    @Id
    @GeneratedValue
    private Integer ccaIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCA_CCG_IDX", referencedColumnName = "ccgIdx")
    private CalculateGroup calculateGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CCA_OG_IDX", referencedColumnName = "ogIdx")
    private OrderGs orderGs;

    private String ccaContent;
    private String ccaReason;
    private int ccaCost;
}

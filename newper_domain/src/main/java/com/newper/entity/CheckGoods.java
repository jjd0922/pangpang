package com.newper.entity;

import com.newper.constant.CgState;
import com.newper.constant.CgsType;
import com.newper.entity.common.CreatedEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CheckGoods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cgsIdx;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CGS_CG_IDX", referencedColumnName = "cgIdx")
    private CheckGroup checkGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CGS_G_IDX", referencedColumnName = "gIdx")
    private Goods goods;


    private String cgsType;

    private int cgsCount;

    private int cgsExpectedCost;

    private int cgsRealCost;
}

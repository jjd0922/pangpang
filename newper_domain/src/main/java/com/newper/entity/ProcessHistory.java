package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.hql.internal.ast.ParameterTranslationsImpl;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProcessHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="PH_PG_IDX", referencedColumnName = "pgIdx")
    private ProcessGroup processGroup;

    private String phState;

    private Date phDate;

    private Time phTime;

}

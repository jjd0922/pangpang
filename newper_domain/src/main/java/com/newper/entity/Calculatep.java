package com.newper.entity;

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
public class Calculatep {

    @Id
    @GeneratedValue
    private  Integer cpIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CP_PO_IDX", referencedColumnName = "poIdx")
    private Po po;
/*    cpcgidx*/

    private String cpState;

    private LocalDate cpDate;

    private LocalDate cpCompleteDate;

    private LocalDate cpCancelDate;



}

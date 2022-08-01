/*package com.newper.entity;

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
public class SpecItem {


    private Integer speciOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPECI_SPEC_IDX", referencedColumnName = "specispecIdx")
    private  Spec spec;

}*/

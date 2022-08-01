package com.newper.entity;

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
public class Location {
    @Id
    private Integer locIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_WH_IDX", referencedColumnName = "locwhIdx")
    private Warehouse warehouse;

    private String locState;
    private String locType;
    private String locCode;
    private String locForm;
    private String locZone;
    private String locRow;
    private String locColumn;

}

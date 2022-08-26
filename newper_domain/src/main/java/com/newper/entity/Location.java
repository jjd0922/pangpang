package com.newper.entity;

import com.newper.constant.LocForm;
import com.newper.constant.LocType;
import com.newper.entity.common.Address;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_WH_IDX", referencedColumnName = "whIdx")
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    private LocType locType;

    private String locCode;

    @Enumerated(EnumType.STRING)
    private LocForm locForm;

    private String locZone;
    private String locRow;
    private String locColumn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_U_IDX", referencedColumnName = "uIdx")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;

    public void updateLocation(Location location) {
        setLocType(location.getLocType());
        setLocCode(location.getLocCode());
        setLocForm(location.getLocForm());
        setLocZone(location.getLocZone());
        setLocRow(location.getLocRow());
        setLocColumn(location.getLocColumn());
    }
}

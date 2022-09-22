package com.newper.entity;

import com.newper.constant.LocForm;
import com.newper.constant.LocType;
import com.newper.entity.common.CreatedEntity;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Location extends CreatedEntity {
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
    private String locColumn;
    private String locRow;
    private Integer locMax;
    private String locBarcode;
    private String locMemo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_U_IDX", referencedColumnName = "uIdx")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.DETACH)
    private List<Goods> goodsList;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if (getLocForm() == null) {
            throw new MsgException("로케이션 형태를 입력해주세요");
        } else if (getLocType() == null) {
            throw new MsgException("로케이션 구분을 입력해주세요");
        } else if (!StringUtils.hasText(getLocCode())) {
            throw new MsgException("로케이션을 입력해주세요");
        }
    }
    
    public void updateLocation(Location location) {
        setLocType(location.getLocType());
        setLocCode(location.getLocCode());
        setLocForm(location.getLocForm());
        setLocZone(location.getLocZone());
        setLocColumn(location.getLocColumn());
        setLocRow(location.getLocRow());
        setLocMax(location.getLocMax());
        setLocBarcode(location.getLocBarcode());
        setLocMemo(location.getLocMemo());
    }
}

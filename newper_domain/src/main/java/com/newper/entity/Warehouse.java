package com.newper.entity;


import com.newper.constant.WhState;
import com.newper.entity.common.AddressEmb;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer whIdx;

    @Enumerated(EnumType.STRING)
    private WhState whState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WH_COM_IDX", referencedColumnName = "comIdx")
    private Company company;

    private String whName;

    @Embedded
    private AddressEmb address;

    @PrePersist
    @PreUpdate
    public void preSave(){
        if (!StringUtils.hasText(getWhName())) {
            throw new MsgException("창고명을 입력해주세요");
        }
    }

}
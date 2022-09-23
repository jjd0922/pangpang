package com.newper.entity;

import com.newper.constant.SaType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SelfAuth {

    @Id @GeneratedValue
    private Long saIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_SHOP_IDX", referencedColumnName = "shopIdx")
    private Shop shop;

    @Enumerated(EnumType.STRING)
    private SaType saType;
    private boolean saUsed;
    private String saReq;
    private String saRes;
    private LocalDate saReqDate;
    private LocalTime saReqTime;

}

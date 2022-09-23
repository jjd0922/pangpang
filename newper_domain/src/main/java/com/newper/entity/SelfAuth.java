package com.newper.entity;

import com.newper.constant.SaType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@DynamicInsert
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
    @Builder.Default
    private Map<String,Object> saReq = new HashMap<>();
    @Builder.Default
    private Map<String,Object> saRes = new HashMap<>();
    private LocalDate saReqDate;
    private LocalTime saReqTime;

}

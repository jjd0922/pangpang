package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderGsGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oggIdx;

    /** spo prefix 없이 {spoIdx}_{spoIdx} 로 구성*/
    private String oggSpo;
    /** 1부터 시작*/
    private int oggCnt;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderGsGroup", cascade = CascadeType.ALL)
    private List<OrderGs> orderGsList = new ArrayList<>();

    public void addOrderGs(OrderGs og) {
        getOrderGsList().add(og);
        og.setOrderGsGroup(this);
    }
}

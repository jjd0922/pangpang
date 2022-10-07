package com.newper.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderGsGroupCancel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oggcIdx;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderGsGroupCancel")
    private List<OrderGs> orderGsList;

}

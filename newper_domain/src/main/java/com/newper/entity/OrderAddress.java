package com.newper.entity;


import com.newper.entity.common.AddressEmb;
import com.newper.exception.MsgException;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderAddress{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oaIdx;

    private String oaName;

    private String oaPhone;

    private String oaMemo;

    private String oaEntrance;

    @Embedded
    private AddressEmb address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderAddress")
    private List<Orders> ordersList;

    @PrePersist
    @PreUpdate
    public void ordersAddressSave(){

        if (!StringUtils.hasText(getOaName())) {
            throw new MsgException("받으시는 분 이름을 입력해주세요.");
        }
        if (!StringUtils.hasText(getOaPhone())) {
            throw new MsgException("받으시는 분 연락처를 입력해주세요.");
        }
        if (!StringUtils.hasText(getAddress().getPost())) {
            throw new MsgException("받으시는 분 주소를 입력해주세요.");
        }

    }

    public void setOrders(Orders orders) {
        List<Orders> ordersList = getOrdersList();
        if (ordersList == null) {
            ordersList = new ArrayList<>();
            setOrdersList(ordersList);
        }
        ordersList.add(orders);
    }
}

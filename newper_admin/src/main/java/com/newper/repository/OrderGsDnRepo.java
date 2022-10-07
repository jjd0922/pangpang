package com.newper.repository;

import com.newper.entity.DeliveryNum;
import com.newper.entity.OrderGs;
import com.newper.entity.OrderGsDn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGsDnRepo extends JpaRepository<OrderGsDn, Long> {
    public OrderGsDn findByOrderGsAndDeliveryNum(OrderGs orderGs, DeliveryNum deliveryNum);

}

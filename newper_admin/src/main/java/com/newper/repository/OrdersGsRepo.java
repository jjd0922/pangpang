package com.newper.repository;

import com.newper.entity.OrderGs;
import com.newper.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersGsRepo extends JpaRepository<OrderGs, Long> {
}
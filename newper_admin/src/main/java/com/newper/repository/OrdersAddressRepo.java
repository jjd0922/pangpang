package com.newper.repository;

import com.newper.entity.OrderAddress;
import com.newper.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersAddressRepo extends JpaRepository<OrderAddress, Long> {
}

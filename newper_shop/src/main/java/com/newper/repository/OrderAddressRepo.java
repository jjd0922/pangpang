package com.newper.repository;

import com.newper.entity.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepo extends JpaRepository<OrderAddress, Long> {
}

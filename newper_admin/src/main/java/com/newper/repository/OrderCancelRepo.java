package com.newper.repository;

import com.newper.entity.OrderCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelRepo extends JpaRepository<OrderCancel, Long> {
}

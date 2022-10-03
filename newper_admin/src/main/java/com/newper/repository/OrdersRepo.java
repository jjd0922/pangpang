package com.newper.repository;

import com.newper.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepo extends JpaRepository<Orders, Long> {
    Orders findByoCode(String oCode);
}

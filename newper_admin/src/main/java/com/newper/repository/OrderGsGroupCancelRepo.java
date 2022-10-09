package com.newper.repository;

import com.newper.entity.OrderCancel;
import com.newper.entity.OrderGsGroupCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderGsGroupCancelRepo extends JpaRepository<OrderGsGroupCancel, Long> {
}

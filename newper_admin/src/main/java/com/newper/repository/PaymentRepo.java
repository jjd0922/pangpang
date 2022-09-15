package com.newper.repository;

import com.newper.entity.OrderGs;
import com.newper.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}

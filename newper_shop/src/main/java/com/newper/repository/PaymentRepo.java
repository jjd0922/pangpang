package com.newper.repository;

import com.newper.entity.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface PaymentRepo extends JpaRepository<Payment, Long> {

    @EntityGraph(attributePaths = {"paymentHistoryList"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Payment findLockByPayIdx(long payIdx);

}

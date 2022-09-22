package com.newper.repository;

import com.newper.entity.PaymentHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface PaymentHistoryRepo extends JpaRepository<PaymentHistory, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PaymentHistory findLockByPhIdx(long phIdx);
}

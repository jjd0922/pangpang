package com.newper.repository;

import com.newper.entity.PaymentHistory;
import com.newper.entity.SelfAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

public interface SelfAuthRepo extends JpaRepository<SelfAuth, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    SelfAuth findLockBySaIdx(long saIdx);

}

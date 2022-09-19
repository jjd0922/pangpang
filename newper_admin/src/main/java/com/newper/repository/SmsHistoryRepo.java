package com.newper.repository;

import com.newper.entity.SmsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsHistoryRepo  extends JpaRepository<SmsHistory, Long> {
    public SmsHistory findSmsHistoryByShIdx(long shIdx);
}

package com.newper.repository;

import com.newper.entity.PoReceived;
import com.newper.entity.ProcessNeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessNeedRepo extends JpaRepository<ProcessNeed, Long> {

}

package com.newper.repository;

import com.newper.constant.PnProcess;
import com.newper.constant.PnType;
import com.newper.entity.PoReceived;
import com.newper.entity.ProcessNeed;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessNeedRepo extends JpaRepository<ProcessNeed, Long> {

    List<ProcessNeed> findByGoods_gIdxAndPnType(long gIdx, PnProcess pnType);
    ProcessNeed findTopByGoods_gIdxAndPnProcessAndPnTypeAndProcessGroup_pgIdxIsNullOrderByPnIdxDesc(long gIdx, String pnProcess, String pnType);

    @EntityGraph(attributePaths = {"goods", "goods.product"})
    List<ProcessNeed> findByProcessGroup_pgIdx(int pgIdx);
}

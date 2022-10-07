package com.newper.repository;

import com.newper.constant.PnProcess;
import com.newper.constant.PnState;
import com.newper.constant.PnType;
import com.newper.entity.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessNeedRepo extends JpaRepository<ProcessNeed, Integer> {

    List<ProcessNeed> findByGoods_gIdxAndPnType(long gIdx, String pnType);

    @EntityGraph(attributePaths = {"goods", "afterService"})
    ProcessNeed findByPnIdx(int pnIdx);

    List<ProcessNeed> findByGoodsAndPnProcessAndPnState(Goods goods, PnProcess pnProcess, PnState pnState);

    ProcessNeed findByGoodsAndPnTypeAndPnProcessAndPnState(Goods goods, PnType pnType, PnProcess pnProcess, PnState pnState);

    List<ProcessNeed> findByProcessGroup(ProcessGroup processGroup);
}

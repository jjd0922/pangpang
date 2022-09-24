package com.newper.repository;

import com.newper.constant.PnProcess;
import com.newper.constant.PnType;
import com.newper.entity.Goods;
import com.newper.entity.PoReceived;
import com.newper.entity.ProcessNeed;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessNeedRepo extends JpaRepository<ProcessNeed, Integer> {

    List<ProcessNeed> findByGoods_gIdxAndPnType(long gIdx, String pnType);


    ProcessNeed findByGoodsAndPnCount(Goods goods, int pnCount);

    @EntityGraph(attributePaths = {"goods"})
    ProcessNeed findByPnIdx(int pnIdx);
}

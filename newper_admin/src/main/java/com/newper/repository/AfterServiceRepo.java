package com.newper.repository;

import com.newper.constant.AsState;
import com.newper.entity.AfterService;
import com.newper.entity.Goods;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AfterServiceRepo extends JpaRepository<AfterService, Long> {
    @EntityGraph(attributePaths = {"deliveryNum", "deliveryNum2", "goods"})
    public AfterService findByAsIdx(long asIdx);

    public AfterService findByGoodsAndAsState(Goods goods, AsState asState);
}

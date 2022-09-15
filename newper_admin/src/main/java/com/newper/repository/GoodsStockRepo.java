package com.newper.repository;

import com.newper.entity.GoodsStock;
import com.newper.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsStockRepo extends JpaRepository<GoodsStock, Integer> {

    @EntityGraph(attributePaths = {"product"})
    public GoodsStock findGoodsStockByGsIdx(Integer gsIdx);

    public GoodsStock findGoodsStockByGsCode(String gsCode);

}

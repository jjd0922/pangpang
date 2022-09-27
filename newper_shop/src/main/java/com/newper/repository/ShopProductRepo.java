package com.newper.repository;

import com.newper.entity.ShopProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductRepo extends JpaRepository<ShopProduct, Long> {


    @EntityGraph(attributePaths = {"category"})
    ShopProduct findInfosBySpIdx(long spIdx);
}

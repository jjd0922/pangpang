package com.newper.repository;

import com.newper.entity.Shop;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepo extends JpaRepository<Shop, Integer> {
    @EntityGraph(attributePaths = {"floatingBarList"})
    public Shop findWithFbByShopIdx(Integer shopIdx);
}

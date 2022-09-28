package com.newper.repository;

import com.newper.entity.ShopProductOption;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductOptionRepo extends JpaRepository<ShopProductOption, Long> {

    @EntityGraph(attributePaths = {"shopProductAdd"})
    ShopProductOption findSpaBySpoIdx(long spoIdx);
}

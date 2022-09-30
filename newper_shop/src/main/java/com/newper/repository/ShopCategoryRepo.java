package com.newper.repository;

import com.newper.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCategoryRepo extends JpaRepository<ShopCategory, Integer> {
    List<ShopCategory> findAllByAndScateOrderGreaterThanOrderByScateOrderAsc(@Param("scateOrder") int scateOrder);
}

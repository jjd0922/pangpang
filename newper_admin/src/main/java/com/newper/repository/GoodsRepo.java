package com.newper.repository;

import com.newper.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepo extends JpaRepository<Goods, Integer> {
}

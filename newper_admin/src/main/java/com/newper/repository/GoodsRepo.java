package com.newper.repository;

import com.newper.entity.Goods;
import com.newper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsRepo extends JpaRepository<Goods, Long> {
    public Goods findBygBarcode(String barcode);
}

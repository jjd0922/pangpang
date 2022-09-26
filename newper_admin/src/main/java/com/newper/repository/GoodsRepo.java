package com.newper.repository;

import com.newper.entity.Goods;
import com.newper.entity.Po;
import com.newper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsRepo extends JpaRepository<Goods, Long> {
    public Goods findBygBarcode(String barcode);

    List<Goods> findByPo(Po po);
}

package com.newper.repository;

import com.newper.entity.CheckGoods;
import com.newper.entity.Po;
import com.newper.entity.PoReceived;
import com.newper.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoReceivedRepo extends JpaRepository<PoReceived, Long> {
    PoReceived findByPoAndProductAndPorSellPrice (Po po, Product product, int porSellPrice);
}

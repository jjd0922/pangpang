package com.newper.repository;

import com.newper.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopProductRepo extends JpaRepository<ShopProduct, Long> {
}
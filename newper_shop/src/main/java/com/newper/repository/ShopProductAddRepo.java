package com.newper.repository;

import com.newper.entity.ShopProduct;
import com.newper.entity.ShopProductAdd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopProductAddRepo extends JpaRepository<ShopProductAdd, Long> {


}

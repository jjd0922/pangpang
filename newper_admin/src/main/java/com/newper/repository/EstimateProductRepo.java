package com.newper.repository;

import com.newper.entity.EstimateProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstimateProductRepo extends JpaRepository<EstimateProduct, Integer> {

}

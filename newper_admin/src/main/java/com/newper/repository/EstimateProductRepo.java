package com.newper.repository;

import com.newper.entity.Estimate;
import com.newper.entity.EstimateProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EstimateProductRepo extends JpaRepository<EstimateProduct, Integer> {
    public List<EstimateProduct> findEstimateProductByEstimate_PeIdx(Integer peIdx);
    public void deleteEstimateProductByEstimate_PeIdx(Integer peIdx);

}

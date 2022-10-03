package com.newper.repository;

import com.newper.entity.CalculateSales;
import com.newper.entity.OrderGs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculateSalesRepo extends JpaRepository<CalculateSales, Integer> {

    public CalculateSales findByOrderGs(OrderGs ordersGs);
}

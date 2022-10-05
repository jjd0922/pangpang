package com.newper.repository;

import com.newper.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByCuId(String cuId);
    boolean existsByCuId(String cuId);
    Customer getReferenceByCuId(String cuId);
    Customer findByCuNameAndCuPhone(String cuName, String cuPhone);

}

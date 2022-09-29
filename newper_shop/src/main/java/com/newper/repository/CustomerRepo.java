package com.newper.repository;

import com.newper.entity.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByCuId(String cuId);

    boolean existsByCuId(String cuId);

    Customer findByCuNameAndCuPhone(String cuName, String cuPhone);

    /** Ci값으로 회원 찾기 (현재는 임시로 di)*/
    Customer findByCuDi(String cuDi);
}

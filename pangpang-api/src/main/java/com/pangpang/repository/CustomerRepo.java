package com.pangpang.repository;

import com.pangpang.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Customer findByCuCi(String ci);
    Customer findByCuNickname(String cuNickname);
}

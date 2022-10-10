package com.newper.repository;

import com.newper.entity.Address;
import com.newper.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.OrderBy;
import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findByCustomerOrderByAdBasicDesc(Customer customer);
}

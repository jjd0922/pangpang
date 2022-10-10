package com.newper.repository;

import com.newper.entity.Cart;
import com.newper.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomerAndCartSpo(Customer customer, String cartSpo);
}

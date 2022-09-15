package com.newper.repository;

import com.newper.entity.Auth;
import com.newper.entity.DeliveryNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryNumRepo extends JpaRepository<DeliveryNumber, Long> {

}

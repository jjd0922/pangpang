package com.newper.repository;

import com.newper.constant.MenuType;
import com.newper.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepo extends JpaRepository<Auth, Integer> {

    List<Auth> findByauthType(MenuType authType);
}

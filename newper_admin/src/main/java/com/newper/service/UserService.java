package com.newper.service;


import com.newper.dto.ParamMap;
import com.newper.entity.User;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {


    @Autowired
    private final UserRepo userRepo;


    @Transactional
    public Integer userCreate(ParamMap paramMap) {
        User user = paramMap.mapParam(User.class);

        userRepo.save(user);

        return user.getUIdx();
    }
}


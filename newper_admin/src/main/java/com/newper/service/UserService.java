package com.newper.service;


import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.User;
import com.newper.repository.CompanyRepo;
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
    private final CompanyRepo companyRepo;

    @Transactional
    public Integer userCreate(ParamMap paramMap) {
        Integer comIdx = (Integer) paramMap.get("comIdx");
      //  companyRepo.fin 어쩌고
        //user.setcompany(comidx)
        //paramMap.put(Company);

        User user = paramMap.mapParam(User.class);
        System.out.println("user: " + user);

        userRepo.save(user);

        return user.getUIdx();
    }


}


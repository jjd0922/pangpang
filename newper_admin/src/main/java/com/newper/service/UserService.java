package com.newper.service;


import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.User;
import com.newper.entity.common.Address;
import com.newper.repository.CompanyRepo;
import com.newper.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {


    private final UserRepo userRepo;
    private final CompanyRepo companyRepo;

/*    @Transactional
    public Integer userCreate(ParamMap paramMap) {
        Integer comIdx = (Integer) paramMap.get("comIdx");
      //  companyRepo.fin 어쩌고
        //user.setcompany(comidx)
        //paramMap.put(Company);

        User user = paramMap.mapParam(User.class);
        System.out.println("user: " + user);

        userRepo.save(user);

        return user.getUIdx();
    }*/

    @Transactional
    public Integer saveUser(ParamMap paramMap) {
        System.out.println("paramMap = " + paramMap);

//        Address address = paramMap.mapParam(Address.class);
//        Company company = paramMap.mapParam(Company.class);
        User user =  paramMap.mapParam(User.class);

        System.out.println(user.getUIdx());



        System.out.println("here1");
//        user.setAddress(address);

        System.out.println("here2");
        User saveUser = userRepo.saveAndFlush(user);
        System.out.println("here3");

        return saveUser.getUIdx();
    }


}


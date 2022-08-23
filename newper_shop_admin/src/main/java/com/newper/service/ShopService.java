package com.newper.service;


import com.newper.constant.CtType;
import com.newper.constant.UState;
import com.newper.constant.UType;
import com.newper.dto.ParamMap;
import com.newper.entity.Auth;
import com.newper.entity.Company;
import com.newper.entity.Shop;
import com.newper.entity.User;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;


@Service
@RequiredArgsConstructor
//@Transactional(readOnly = true)
public class ShopService {

    private final ShopRepo shopRepo;

    /**분양몰 추가*/
    @Transactional
    public int shopSave(ParamMap paramMap){
        Shop shop = paramMap.mapParam(Shop.class);
        shop.setShopMileage(0F);
        shop.setShopBasket("Y");
        Calendar cal = Calendar.getInstance();
        shop.setShopName("test_"+cal.getTime());
        shopRepo.save(shop);
        return shop.getShopIdx();
    }



}



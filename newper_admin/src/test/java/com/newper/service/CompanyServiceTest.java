package com.newper.service;

import com.newper.component.SessionInfo;
import com.newper.constant.ComState;
import com.newper.constant.ComType;
import com.newper.entity.Company;
import com.newper.entity.Menu;
import com.newper.entity.SubMenu;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.repository.CompanyRepo;
import com.newper.repository.MenuRepo;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openxmlformats.schemas.drawingml.x2006.main.STPitchFamily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CompanyServiceTest {

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    MenuRepo menuRepo;

    @BeforeEach
    public void setId(){
        sessionInfo.setId("testId");
    }

    @Test
    public void companyNameTest(){
        Company company = Company.builder()
                .comName("tt")
                .comType(ComType.CORPO)
                .comMid("test")
                .comState(ComState.NORMAL)
                .comCeo("ceo")
                .comTel("1234")
                .comBank("test")
                .comAccount("123123")
                .address(Address.builder()
                        .addr1("123")
                        .addr2("123")
                        .addr3("123")
                        .addr4("123")
                        .post("123")
                        .build())
                .comNum("1234")
                .comTel("1234")
                .build();

        try{
            companyRepo.save(company);
        }catch (DataIntegrityViolationException dv){
            System.out.println("uk duplicated");
        }

        System.out.println("end!!");
    }

    @Test
    @Transactional
    public void menuTest(){
        Menu home = menuRepo.findById("HOME").get();
        SubMenu subMenu = home.getSubMenuList().get(0);
        List<Long> smAuth = subMenu.getSmAuth();
        Integer  i=1;
        Long k=1l;
        System.out.println( i.intValue() == k.intValue());


        for (Long integer : smAuth) {
            System.out.println(integer);
        }

    }

}
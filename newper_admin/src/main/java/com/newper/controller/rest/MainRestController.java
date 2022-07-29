package com.newper.controller.rest;

import com.newper.component.MenuList;
import com.newper.component.SessionInfo;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Menu;
import com.newper.entity.SubMenu;
import com.newper.exception.MsgException;
import com.newper.exception.NoSessionException;
import com.newper.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainRestController {

    private final MenuRepo menuRepo;

    @Autowired
    private SessionInfo sessionInfo;

    /** menu list update*/
    @PostConstruct
    @GetMapping("updateMenu.ajax")
    public void updateMenu(){
        List<Menu> menuList = menuRepo.findMenuALl();

        MenuList.menus = menuList;
    }

    /** 로그인 처리*/
    @PostMapping(value = "login.ajax")
    public ReturnMap login(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        String id = paramMap.getString("id");

        if(StringUtils.hasText(id)){
            sessionInfo.setId(id);
            rm.setLocation("/home");
        }else{
            rm.setMessage("ID를 입력해주세요");
        }

        return rm;
    }
}

package com.newper.controller.rest;

import com.newper.component.SessionInfo;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainRestController {


    @Autowired
    private SessionInfo sessionInfo;

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

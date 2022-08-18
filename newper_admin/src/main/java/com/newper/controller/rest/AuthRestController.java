package com.newper.controller.rest;

import com.newper.dto.ReturnMap;
import com.newper.repository.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/auth/")
@RequiredArgsConstructor
@RestController
public class AuthRestController {

    private final AuthRepo authRepo;

    /** auth 권한관리에서 권한 리스트 로딩*/
    @PostMapping("list.ajax")
    public ReturnMap list(String authType){
        ReturnMap rm = new ReturnMap();

        rm.put("list", authRepo.findAll());

        return rm;
    }
    /** auth idx로 auth 조회*/
    @PostMapping("info.ajax")
    public Object info(int authIdx){
        ReturnMap rm = new ReturnMap();

        rm.put("auth", authRepo.findById(authIdx));

        return rm;
    }


}

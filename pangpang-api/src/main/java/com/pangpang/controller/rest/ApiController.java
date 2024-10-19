package com.pangpang.controller.rest;

import com.pangpang.dto.ReturnMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "")
@RestController
@RequiredArgsConstructor
public class ApiController {

    @GetMapping("/health")
    public ReturnMap healthCheck(){
        ReturnMap rm = new ReturnMap();
        return rm;
    }

    @GetMapping("")
    public String main(){
        return "집나들이 API";
    }

}

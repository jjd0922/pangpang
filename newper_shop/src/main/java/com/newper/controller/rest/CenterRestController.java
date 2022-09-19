package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/center/")
public class CenterRestController {

    @PostMapping("notice.ajax")
    public void test(){
        System.out.println("test in~~~");

    }
}

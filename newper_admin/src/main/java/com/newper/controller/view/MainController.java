package com.newper.controller.view;

import com.newper.constant.ComState;
import com.newper.entity.Company;
import com.newper.repository.CompanyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CompanyRepo companyRepo;

    @GetMapping(value = {"","index"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index");

        Company com = Company.builder()
                .comState(ComState.TEST2)
                .build();

        companyRepo.save(com);

        return mav;
    }

}

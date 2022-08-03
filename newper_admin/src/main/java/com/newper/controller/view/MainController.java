package com.newper.controller.view;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final CompanyRepo companyRepo;

    @Autowired
    private SessionInfo sessionInfo;

    @GetMapping(value = {"", "index"})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("main/index");


        return mav;
    }

    @GetMapping(value = "home")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("main/home");


        return mav;
    }


    @RequestMapping(value = "defaultSample")
    public ModelAndView defaultSample() {
        ModelAndView mav = new ModelAndView("main/default_sample");

        return mav;
    }

    @RequestMapping(value = "/popup/")
    public ModelAndView popupSample() {
        ModelAndView mav = new ModelAndView("main/popup_sample");

        return mav;
    }

    @PostMapping("summernote/image")
    public ModelAndView summernoteImage(MultipartFile[] summernoteImageFile){
        ModelAndView mav=new ModelAndView("main/summernote_image");
        List<String> nameList = new ArrayList<String>();
        for (MultipartFile multipartFile : summernoteImageFile) {
            String fileName = Common.uploadFilePath(multipartFile,"summernote/", AdminBucket.OPEN);
            nameList.add(fileName);
            System.out.println(fileName);
        }
        mav.addObject("nameList", nameList);
        return mav;
    }
}
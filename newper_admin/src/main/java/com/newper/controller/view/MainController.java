package com.newper.controller.view;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
import com.newper.controller.NoLogin;
import com.newper.entity.AesEncrypt;
import com.newper.repository.CompanyRepo;
import com.newper.repository.MenuRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final CompanyRepo companyRepo;

    @Value("${newper.shop.domain}")
    private String shopDomain;
    @Autowired
    private SessionInfo sessionInfo;

    @NoLogin
    @GetMapping("logout")
    public ModelAndView logout(HttpServletRequest request){
        request.getSession().invalidate();
        return new ModelAndView("redirect:/index");
    }
    @NoLogin
    @GetMapping(value = {"", "index", "loginPop"})
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("main/index");


        return mav;
    }
    /** login 팝업 띄우고 뒤로가기*/
    @NoLogin
    @GetMapping(value = "needLogin")
    public ModelAndView needLogin() {
        ModelAndView mav = new ModelAndView("main/needLogin");

        return mav;
    }
    /** SHOP ADMIN으로 이동*/
    @NoLogin
    @GetMapping(value = "shopAdmin")
    public ModelAndView shopAdmin() {
        String sso;
        if (sessionInfo.getIdx() != null) {
            AesEncrypt aesEncrypt = new AesEncrypt();
            sso = aesEncrypt.encryptRandom(sessionInfo.getIdx() + "_" + System.currentTimeMillis());
        }else{
            sso = "";
        }

        ModelAndView mav = new ModelAndView("redirect:"+shopDomain+"?sso="+sso);
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
        }
        mav.addObject("nameList", nameList);
        return mav;
    }
}
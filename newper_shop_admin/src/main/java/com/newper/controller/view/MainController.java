package com.newper.controller.view;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
import com.newper.controller.NoLogin;
import com.newper.dto.ParamMap;
import com.newper.entity.AesEncrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${newper.domain}")
    private String adminDomain;
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
    public ModelAndView index(ParamMap paramMap) {
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

    @GetMapping(value = "home")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("main/home");

        System.out.println("sessionInfo.getIdx()");
        System.out.println(sessionInfo.getIdx());
        return mav;
    }

    /** erp ADMIN으로 이동*/
    @NoLogin
    @GetMapping(value = "erpAdmin")
    public ModelAndView erpAdmin() {
        String sso;
        if (sessionInfo.getIdx() != null) {
            AesEncrypt aesEncrypt = new AesEncrypt();
            sso = aesEncrypt.encryptRandom(sessionInfo.getIdx() + "_" + System.currentTimeMillis());
        }else{
            sso = "";
        }

        ModelAndView mav = new ModelAndView("redirect:"+adminDomain+"?sso="+sso);
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

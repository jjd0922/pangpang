package com.newper.controller.view;

import com.newper.component.AdminBucket;
import com.newper.exception.MsgException;
import com.newper.storage.NewperStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

@RequestMapping(value = "/company/")
@Controller
@RequiredArgsConstructor
public class CompanyController {

    /** 거래처 관리 페이지*/
    @GetMapping(value = "")
    public ModelAndView company(){
        ModelAndView mav = new ModelAndView("company/company");

        return mav;
    }

    /** 거래처 신규등록 팝업 */
    @GetMapping(value = "regist")
    public ModelAndView regist(MultipartFile mf){
        ModelAndView mav = new ModelAndView("company/regist");

        return mav;
    }
    @PostMapping(value = "regist")
    public ModelAndView registPost(MultipartFile comNumFile){
        ModelAndView mav = new ModelAndView("company/regist");

        try{
            String s = NewperStorage.uploadFile(AdminBucket.OPEN, "company/com_num/" + comNumFile.getOriginalFilename(), comNumFile.getInputStream(), comNumFile.getSize(), comNumFile.getContentType());
            System.out.println(s);
            System.out.println("파일 업로드 완료");
        }catch (IOException ioe){
            throw new MsgException("잠시 후 시도해주세");
        }

        return mav;
    }

    /** 거래처 계약관리 조회 페이지 **/
    @GetMapping(value = "contract")
    public ModelAndView contract(){
        ModelAndView mav = new ModelAndView("company/contract");

        return mav;
    }
}

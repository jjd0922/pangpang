package com.newper.controller.view;

import com.newper.component.AdminBucket;
import com.newper.dto.ParamMap;
import com.newper.entity.CompanyEmployee;
import com.newper.repository.CompanyEmployeeRepo;
import com.newper.repository.CompanyRepo;
import com.newper.service.CompanyService;
import com.newper.storage.NewperBucket;
import com.newper.storage.NewperStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/company/")
@Controller
@RequiredArgsConstructor
public class CompanyController {


    private final CompanyService companyService;
    private final CompanyEmployeeRepo companyEmployeeRepo;

    /** 거래처 관리 페이지*/
    @GetMapping(value = "")
    public ModelAndView company(){
        ModelAndView mav = new ModelAndView("company/company");

        return mav;
    }

    /** 거래처 신규등록 팝업 */
    @GetMapping(value = "regist")
    public ModelAndView regist(){
        ModelAndView mav = new ModelAndView("company/regist");

        return mav;
    }

    /** 거래처 신규등록 처리 */
    @PostMapping(value = "regist")
    public ModelAndView registPost(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("company/regist");

        // 담당자(CompanyEmployee) insert
        CompanyEmployee companyEmployee = paramMap.mapParam(CompanyEmployee.class);
        companyEmployeeRepo.save(companyEmployee);

        // company insert
        paramMap.put("companyEmployee", companyEmployee);
        companyService.saveCompany(paramMap, comNumFile, comAccountFile);


//        try {
//            NewperStorage.download("test", response.getOutputStream());
//        } catch (IOException ioException) {
//
//        }


        return mav;
    }

    /** 거래처 계약관리 조회 페이지 **/
    @GetMapping(value = "contract")
    public ModelAndView contract(){
        ModelAndView mav = new ModelAndView("company/contract");

        return mav;
    }
}

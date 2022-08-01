package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.CompanyEmployee;
import com.newper.repository.CompanyEmployeeRepo;
import com.newper.repository.ContractRepo;
import com.newper.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/company/")
@Controller
@RequiredArgsConstructor
public class CompanyController {


    private final CompanyService companyService;
    private final ContractRepo contractRepo;
    private final CompanyRepo companyRepo;
    private final CompanyEmployeeRepo companyEmployeeRepo;

    /** 거래처 관리 페이지*/
    @GetMapping(value = "")
    public ModelAndView company(){
        ModelAndView mav = new ModelAndView("company/company");

        return mav;
    }

    /** 거래처 신규등록 팝업 */
    @GetMapping(value = "regist")
    public ModelAndView registPop() {
        ModelAndView mav = new ModelAndView("company/pop");

        return mav;
    }

    /** 거래처 신규등록 처리 */
    @PostMapping(value = "regist")
    public ModelAndView registPost(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("company/pop");

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

    /**거래처 상세조회 페이지 */
    @GetMapping("{comIdx}")
    @Transactional // 여기다.. 하는게 맞나?
    public ModelAndView detail(@PathVariable Integer comIdx) {
        ModelAndView mav = new ModelAndView("company/pop");
        Company company = companyRepo.findCompanyByComIdx(comIdx);

        mav.addObject("company", company);

        return mav;
    }

    /** 거래처 계약관리 조회 페이지 **/
    @GetMapping(value = "contract")
    public ModelAndView contract(){
        ModelAndView mav = new ModelAndView("company/contract");

        return mav;
    }

    /** 거래처 계약관리 등록 페이지 **/
    @GetMapping(value = "contractNew")
    public ModelAndView contractNew(){
        ModelAndView mav = new ModelAndView("company/contractNew");

        return mav;
    }

    /** 거래처 계약서 생성 **/
    @PostMapping(value = "contractNew")
    public ModelAndView contractNewPost(ParamMap paramMap, MultipartFile ccContractFile){
        ModelAndView mav = new ModelAndView("company/contractNew");
        companyService.saveContract(paramMap, ccContractFile);

        return mav;
    }

    /** 거래처 계약관리 상세 페이지 **/
    @GetMapping(value = "contractDetail/{cc_idx}")
    public ModelAndView contractDetail(@PathVariable int cc_idx){
        ModelAndView mav = new ModelAndView("company/contractDetail");
        mav.addObject("detail", contractRepo.findById(cc_idx));
        return mav;
    }

    /** 거래처 계약관리 상세 페이지 **/
    @PostMapping(value = "contractDetail/{cc_idx}")
    public ModelAndView contractDetailPost(@PathVariable int cc_idx, ParamMap paramMap, MultipartFile ccContractFile){
        ModelAndView mav = new ModelAndView("company/contractDetail");
        mav.addObject("detail", contractRepo.findById(cc_idx));
        companyService.saveContract(paramMap, ccContractFile);

        return mav;
    }
}

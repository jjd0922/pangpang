package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.entity.CompanyEmployee;
import com.newper.repository.CompanyEmployeeRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.ContractRepo;
import com.newper.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    @GetMapping(value = "companyPop")
    public ModelAndView companyPop() {
        ModelAndView mav = new ModelAndView("company/companyPop");

        return mav;
    }

    /** 거래처 신규등록 처리 */
    @PostMapping(value = "companyPop")
    public ModelAndView registPost(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        ModelAndView mav = new ModelAndView("main/alertMove");

        // 담당자(CompanyEmployee) insert
        CompanyEmployee companyEmployee = paramMap.mapParam(CompanyEmployee.class);
        companyEmployeeRepo.save(companyEmployee);

        // company insert
        paramMap.put("companyEmployee", companyEmployee);
        Integer comIdx = companyService.saveCompany(paramMap, comNumFile, comAccountFile);


//        try {
//            NewperStorage.download("test", response.getOutputStream());
//        } catch (IOException ioException) {
//
//        }

        mav.addObject("msg", "등록완료");
        mav.addObject("loc", "companyPop/" + comIdx);
        return mav;
    }

    /**거래처 상세조회 페이지 */
    @GetMapping(value = "companyPop/{comIdx}")
    public ModelAndView detail(@PathVariable Integer comIdx) {
        ModelAndView mav = new ModelAndView("company/companyPop");
        Company company = companyRepo.findCompanyByComIdx(comIdx);

        mav.addObject("company", company);
        return mav;
    }

    /** 거래처 수정 처리 */
    @PostMapping("companyPop/{comIdx}")
    public ModelAndView modify(@PathVariable Integer comIdx, ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        ModelAndView mav = new ModelAndView("main/alertMove");
        companyService.updateCompany(comIdx, paramMap, comNumFile, comAccountFile);

        mav.addObject("msg", "수정완료");
        return mav;
    }

    /** 거래처 계약관리 조회 페이지 **/
    @GetMapping(value = "contract")
    public ModelAndView contract(){
        ModelAndView mav = new ModelAndView("company/contract");

        return mav;
    }

    /** 거래처 계약관리 등록 페이지 **/
    @GetMapping(value = "contractPop")
    public ModelAndView contractNew(){
        ModelAndView mav = new ModelAndView("company/contractPop");

        return mav;
    }

    /** 거래처 계약서 생성 **/
    @PostMapping(value = "contractPop")
    public ModelAndView contractNewPost(ParamMap paramMap, MultipartFile ccContractFile){
        ModelAndView mav = new ModelAndView("company/contractPop");
        companyService.saveContract(paramMap, ccContractFile);

        return mav;
    }

    /** 거래처 계약관리 상세 페이지 **/
    @GetMapping(value = "contractPop/{cc_idx}")
    public ModelAndView contractDetail(@PathVariable int cc_idx){
        ModelAndView mav = new ModelAndView("company/contractPop");
        mav.addObject("detail", contractRepo.findContractByccIdx(cc_idx));
        return mav;
    }

    /** 거래처 계약관리 수정 처리 **/
    @PostMapping(value = "contractPop/{cc_idx}")
    public ModelAndView contractDetailPost(@PathVariable int cc_idx, ParamMap paramMap, MultipartFile ccContractFile){
        ModelAndView mav = new ModelAndView("main/alertMove");
        companyService.updateContract(cc_idx, paramMap, ccContractFile);

        mav.addObject("msg", "수정완료");

        return mav;
    }

    /** 카테고리별 입점사 수수료 조회 페이지 **/
    @GetMapping(value = "fee")
    public ModelAndView fee() {
        ModelAndView mav = new ModelAndView("company/fee");

        return mav;
    }
}

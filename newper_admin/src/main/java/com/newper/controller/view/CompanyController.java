package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.CompanyEmployee;
import com.newper.entity.Insurance;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.*;
import com.newper.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/company/")
@Controller
@RequiredArgsConstructor
public class CompanyController {


    private final CompanyService companyService;
    private final CompanyMapper companyMapper;
    private final ContractRepo contractRepo;
    private final CompanyRepo companyRepo;
    private final InsuranceRepo insuranceRepo;
    private final CategoryMapper categoryMapper;

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
//        ModelAndView mav = new ModelAndView("main/alertMove");
        ModelAndView mav = new ModelAndView("main/alertClose");

        paramMap.multiSelect("ctType");

        if (paramMap.getList("ctType").isEmpty()) {
            throw new MsgException("거래처구분코드를 입력해주세요.");
        }

        if (comNumFile.isEmpty()) {
            throw new MsgException("사업자등록증 파일을 첨부해 주세요");
        }

        if (comAccountFile.isEmpty()) {
            throw new MsgException("통장사본 파일을 첨부해 주세요");
        }

        // company insert
        Integer comIdx = companyService.saveCompany(paramMap, comNumFile, comAccountFile);

        mav.addObject("msg", "등록완료");
        mav.addObject("loc", "/company/companyPop/" + comIdx);
        return mav;
    }

    /**거래처 상세조회 페이지 */
    @GetMapping(value = "companyPop/{comIdx}")
    public ModelAndView detail(@PathVariable Integer comIdx) {
        ModelAndView mav = new ModelAndView("company/companyPop");

        Company company = companyRepo.findCompanyByComIdx(comIdx);
        List<String> ctTypes = companyMapper.selectCompanyType(comIdx);

        mav.addObject("ctTypes", ctTypes);

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
    public ModelAndView contractNewPost(ParamMap paramMap, MultipartFile ccFile){
        ModelAndView mav = new ModelAndView("company/contractPop");

        if (ccFile.getSize() == 0) {
            throw new MsgException("계약서 파일을 첨부해 주세요");
        }
        Integer ccIdx = companyService.saveContract(paramMap, ccFile);
        mav.addObject("msg", "등록완료");
        mav.addObject("loc", ccIdx);

        return mav;
    }

    /** 거래처 계약관리 상세 페이지 **/
    @GetMapping(value = "contractPop/{ccIdx}")
    public ModelAndView contractDetail(@PathVariable int ccIdx){
        ModelAndView mav = new ModelAndView("company/contractPop");
        mav.addObject("contract", contractRepo.findContractByccIdx(ccIdx));
        return mav;
    }

    /** 거래처 계약관리 수정 처리 **/
    @PostMapping(value = "contractPop/{ccIdx}")
    public ModelAndView contractDetailPost(@PathVariable Integer ccIdx, ParamMap paramMap, MultipartFile ccFile){
        ModelAndView mav = new ModelAndView("main/alertMove");
        Integer newCcidx = companyService.updateContract(ccIdx, paramMap, ccFile);

        mav.addObject("msg", "수정완료");
        mav.addObject("loc", newCcidx);
        return mav;
    }

    /** 카테고리별 입점사 수수료 조회 페이지 **/
    @GetMapping(value = "fee")
    public ModelAndView fee() {
        ModelAndView mav = new ModelAndView("company/fee");

        mav.addObject("category", categoryMapper.selectCategoryListByCateDepth(2));

        return mav;
    }


    /**매입처 보증보험관리 페이지**/
    @GetMapping(value="insurance")
    public ModelAndView insurance() {
        ModelAndView mav = new ModelAndView("company/insurance");

        return mav;
    }
    
    /**매입처 보증보험관리 신규등록 팝업**/
    @GetMapping(value="insurancePop")
    public ModelAndView insuranceNew() {
        ModelAndView mav = new ModelAndView("company/insurancePop");
    
        return mav;
    }
    
    /**매입처 보증보험관리 신규등록 처리**/
    @PostMapping(value = "insurancePop")
    public ModelAndView insuranceNewPost(ParamMap paramMap, MultipartFile ciFile) {
        System.out.println("paramMap = " + paramMap);
        ModelAndView mav = new ModelAndView("main/alertMove");

        Integer ciIdx = companyService.saveInsurance(paramMap, ciFile);

        mav.addObject("msg", "등록완료");
        mav.addObject("loc", "insurancePop/" + ciIdx);

        return mav;
    }
    

    /**매입처 보증보험관리 상세조회**/
    @GetMapping(value="insurancePop/{ciIdx}")
    public ModelAndView insuranceDetail(@PathVariable Integer ciIdx) {
        ModelAndView mav = new ModelAndView("company/insurancePop");
        mav.addObject("insurance", insuranceRepo.findInsuranceByCiIdx(ciIdx));
        return mav;
    }

    /**매입처 보증보험관리 수정처리**/
    @PostMapping(value="insurancePop/{ciIdx}")
    public ModelAndView insuranceDetailPost(@PathVariable Integer ciIdx, ParamMap paramMap, MultipartFile ciFile) {
        ModelAndView mav = new ModelAndView("main/alertMove");
        companyService.updateInsurance(ciIdx, paramMap, ciFile);

        mav.addObject("msg", "수정완료");
        return mav;
    }

}

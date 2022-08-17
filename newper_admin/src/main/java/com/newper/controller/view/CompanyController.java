package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.exception.MsgException;
import com.newper.mapper.CategoryMapper;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.CompanyInsuranceRepo;
import com.newper.repository.CompanyRepo;
import com.newper.repository.CompanyContractRepo;
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
    private final CompanyContractRepo companyContractRepo;
    private final CompanyRepo companyRepo;
    private final CompanyInsuranceRepo companyInsuranceRepo;
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

    /**거래처 상세조회 페이지 */
    @GetMapping(value = "companyPop/{comIdx}")
    public ModelAndView detail(@PathVariable Integer comIdx) {
        ModelAndView mav = new ModelAndView("company/companyPop");

        Company company = companyRepo.findCompanyByComIdx(comIdx);
        List<Map<String, Object>> ctTypes = companyMapper.selectCompanyType(comIdx);

        if (!ctTypes.isEmpty()) {
            mav.addObject("ctType", ctTypes);
        }
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
    @GetMapping(value = "contractPop")
    public ModelAndView contractNew(){
        ModelAndView mav = new ModelAndView("company/contractPop");

        return mav;
    }

    /** 거래처 계약관리 상세 페이지 **/
    @GetMapping(value = "contractPop/{ccIdx}")
    public ModelAndView contractDetail(@PathVariable int ccIdx){
        ModelAndView mav = new ModelAndView("company/contractPop");
        mav.addObject("contract", companyContractRepo.findContractByccIdx(ccIdx));
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

    /**매입처 보증보험관리 상세조회**/
    @GetMapping(value="insurancePop/{ciIdx}")
    public ModelAndView insuranceDetail(@PathVariable Integer ciIdx) {
        ModelAndView mav = new ModelAndView("company/insurancePop");
        mav.addObject("insurance", companyInsuranceRepo.findInsuranceByCiIdx(ciIdx));
        return mav;
    }

}

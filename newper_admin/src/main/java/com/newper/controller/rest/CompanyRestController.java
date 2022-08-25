package com.newper.controller.rest;

import com.newper.constant.UType;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Company;
import com.newper.exception.MsgException;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.UserMapper;
import com.newper.repository.CompanyEmployeeRepo;
import com.newper.repository.CompanyRepo;
import com.newper.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/company/")
@RestController
@RequiredArgsConstructor
public class CompanyRestController {
    private final CompanyMapper companyMapper;
    private final CompanyService companyService;
    private final UserMapper userMapper;

    private final CompanyRepo companyRepo;

    /**거래처 관리 데이터테이블*/
    @PostMapping("company.dataTable")
    public ReturnDatatable company(ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("거래처관리");

        paramMap.multiSelect("ctType");
        paramMap.multiSelect("comType");
        paramMap.multiSelect("comState");

        rd.setData(companyMapper.selectCompanyDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatable(paramMap.getMap()));

        return rd;
    }

    /** 거래처 신규등록 처리 */
    @PostMapping(value = "companyPop.ajax")
    public ReturnMap registPost(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        ReturnMap rm = new ReturnMap();
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

        rm.setMessage("등록완료");
        rm.setLocation("/company/companyPop/"+comIdx);
        return rm;
    }

    /** 거래처 수정 처리 */
    @PostMapping("companyPop/{comIdx}.ajax")
    public ReturnMap modify(@PathVariable Integer comIdx, ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        ReturnMap rm = new ReturnMap();
        companyService.updateCompany(comIdx, paramMap, comNumFile, comAccountFile);

        rm.setMessage("수정완료");
        return rm;
    }

    @PostMapping("contract.dataTable")
    public ReturnDatatable companyContract(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("거래처 계약관리");

        paramMap.multiSelect("ccState");
        paramMap.multiSelect("ccType");
        paramMap.multiSelect("ccCalType");
        paramMap.multiSelect("ccCycle");

        rd.setData(companyMapper.selectCompanyContract(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyContract(paramMap.getMap()));
        return rd;
    }

    /** 거래처 계약서 생성 **/
    @PostMapping(value = "contractPop.ajax")
    public ReturnMap contractNewPost(ParamMap paramMap, MultipartFile ccFile){
        ReturnMap rm = new ReturnMap();
        if (ccFile.getSize() == 0) {
            throw new MsgException("계약서 파일을 첨부해 주세요");
        }
        Integer ccIdx = companyService.saveContract(paramMap, ccFile);

        rm.setMessage("등록완료");
        rm.setLocation("/company/contractPop/" + ccIdx);
        return rm;
    }

    /** 거래처 계약관리 수정 처리 **/
    @PostMapping(value = "contractPop/{ccIdx}.ajax")
    public ReturnMap contractDetailPost(@PathVariable Integer ccIdx, ParamMap paramMap, MultipartFile ccFile){
        ReturnMap rm = new ReturnMap();
        Integer newCcidx = companyService.updateContract(ccIdx, paramMap, ccFile);

        rm.setMessage("수정완료");
        rm.setLocation("/company/contractPop/" + newCcidx);
        return rm;
    }

    /**카테고리별 입점사 수수료관리 페이지 > 입점사 목록 가져오기*/
    @PostMapping("store.dataTable")
    public ReturnDatatable store(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("카테고리별 입점사 수수료관리 > 입점사 목록");

        rd.setData(companyMapper.selectStoreDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countStoreDatatable(paramMap.getMap()));
        return rd;
    }


    /**카테고리별  입점사 수수료관리 페이지 > 카테고리별 수수료 데이터테이블*/
    @PostMapping("fee.dataTable")
    public ReturnDatatable fee(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("카테고리별 입점사 수수료관리");

        String comIdx = paramMap.get("comIdx").toString();
        System.out.println("comIdx = " + comIdx);

        paramMap.multiSelect("cateIdx");
        paramMap.multiSelect("cfType");

        rd.setData(companyMapper.selectFeeDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countFeeDatatable(paramMap.getMap()));

        return rd;
    }

    /**카테고리별 입점처 수수료 등록**/
    @PostMapping(value = "fee.ajax")
    public ReturnMap saveFee(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        companyService.saveFee(paramMap);

        rm.setMessage("등록완료");

        return rm;
    }

    /**카테고리별 입점처 수수료 수정**/
    @PostMapping(value = "fee/{cfIdx}.ajax")
    public ReturnMap updateFee(@PathVariable Integer cfIdx, ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        companyService.updateFee(cfIdx, paramMap);

        rm.setMessage("수정완료");

        return rm;
    }

    /**매입처보증보험관리 데이터테이블**/
    @PostMapping("insurance.dataTable")
    public ReturnDatatable insurance(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("매입처보증보험관리");

        rd.setData(companyMapper.selectInsuranceDatatable(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countInsuranceDatatable(paramMap.getMap()));
        return rd;
    }

    /**매입처 보증보험관리 신규등록 처리**/
    @PostMapping(value = "insurancePop.ajax")
    public ReturnMap insuranceNewPost(ParamMap paramMap, MultipartFile ciFile) {
        ReturnMap rm = new ReturnMap();
        Integer ciIdx = companyService.saveInsurance(paramMap, ciFile);

        rm.setMessage("등록완료");
        rm.setLocation("/company/insurancePop/" + ciIdx);
        return rm;
    }

    /**매입처 보증보험관리 수정처리**/
    @PostMapping(value="insurancePop/{ciIdx}.ajax")
    public ReturnMap insuranceDetailPost(@PathVariable Integer ciIdx, ParamMap paramMap, MultipartFile ciFile) {
        ReturnMap rm = new ReturnMap();
        companyService.updateInsurance(ciIdx, paramMap, ciFile);

        rm.setMessage("수정완료");
        return rm;
    }

    /**CT_TYPE별 데이터테이블*/
    @PostMapping("companyCtType.dataTable")
    public ReturnDatatable companyCtType(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();

        paramMap.multiSelect("COM_TYPE");
        paramMap.multiSelect("COM_STATE");

        rd.setData(companyMapper.selectCompanyDatatableByCtType(paramMap.getMap()));
        rd.setRecordsTotal(companyMapper.countCompanyDatatableByCtType(paramMap.getMap()));
        return rd;
    }

//    /**comIdx로 거래처 조회*/
//    @PostMapping("companyByComIdx.ajax")
//    public ReturnMap companyByComIdx(int comIdx){
//        ReturnMap rm = new ReturnMap();
//        Company company = companyRepo.findCompanyByComIdx(comIdx);
//        rm.put("COM_NAME",company.getComName());
//        rm.put("COM_IDX",company.getComIdx());
//        return rm;
//    }


}

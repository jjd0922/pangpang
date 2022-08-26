package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.CcState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final CompanyContractRepo companyContractRepo;
    private final CompanyInsuranceRepo companyInsuranceRepo;
    private final CompanyFeeRepo companyFeeRepo;
    private final CompanyEmployeeRepo companyEmployeeRepo;

    /** 거래처 등록 기능 */
    @Transactional
    public Integer saveCompany(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        // employee, address
        Integer ceIdx = saveEmployee(paramMap);
        CompanyEmployee companyEmployee = CompanyEmployee.builder().ceIdx(ceIdx).build();
        Address address = paramMap.mapParam(Address.class);
        Company company = paramMap.mapParam(Company.class);
        company.setCompanyEmployee(companyEmployee);
        company.setAddress(address);

        // 파일업로드
        String comNumFilePath = Common.uploadFilePath(comNumFile, "company/com_num/", AdminBucket.SECRET);
        String comAccountFilePath = Common.uploadFilePath(comAccountFile, "company/com_account/", AdminBucket.SECRET);

        company.setComNumFile(comNumFilePath);
        company.setComNumFileName(comNumFile.getOriginalFilename());
        company.setComAccountFile(comAccountFilePath);
        company.setComAccountFileName(comAccountFile.getOriginalFilename());

        // 구매담당자
        if (StringUtils.hasText(paramMap.getString("uIdx"))) {
            User user = User.builder().uIdx(paramMap.getInt("uIdx")).build();
            company.setUser(user);
        }

        Company savedCompany = companyRepo.saveAndFlush(company);

        // companyType insert
        int comIdx = savedCompany.getComIdx();
        paramMap.put("comIdx", comIdx);
        companyMapper.insertCompanyType(paramMap.getMap());

        return comIdx;
    }

    /**거래처담당자 insert*/
    @Transactional
    public Integer saveEmployee(ParamMap paramMap) {
        CompanyEmployee companyEmployee = paramMap.mapParam(CompanyEmployee.class);
        companyEmployeeRepo.save(companyEmployee);
        return companyEmployee.getCeIdx();
    }

    @Transactional
    public void updateCompany(Integer comIdx, ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        Company company = companyRepo.findById(comIdx).orElseThrow(() -> new MsgException("존재하지 않는 거래처입니다."));

        // 담당자(CompanyEmployee) update
        CompanyEmployee companyEmployee = company.getCompanyEmployee();
        CompanyEmployee ceParam = paramMap.mapParam(CompanyEmployee.class);
        companyEmployee.ceAllUpdate(ceParam);

        // company update
        Address address = paramMap.mapParam(Address.class);
        Company companyParam = paramMap.mapParam(Company.class);

        if (StringUtils.hasText(paramMap.getString("uIdx"))) {
            User user = User.builder().uIdx(paramMap.getInt("uIdx")).build();
            company.setUser(user);
        }

        if (!comNumFile.isEmpty()) {
            String comNumFilePath = Common.uploadFilePath(comNumFile, "company/com_num/", AdminBucket.SECRET);
            company.setComNumFile(comNumFilePath);
            company.setComNumFileName(comNumFile.getOriginalFilename());
        }

        if (!comAccountFile.isEmpty()) {
            String comAccountFilePath = Common.uploadFilePath(comAccountFile, "company/com_account/", AdminBucket.SECRET);
            company.setComAccountFile(comAccountFilePath);
            company.setComAccountFileName(comAccountFilePath);
        }

        company.updateCompany(companyParam, address, companyEmployee);
    }


    @Transactional
    public Integer saveContract(ParamMap paramMap, MultipartFile ccFile) {
        CompanyContract contract = paramMap.mapParam(CompanyContract.class);
        contract.setCcStart(LocalDate.parse(paramMap.getString("ccStart")));
        contract.setCcEnd(LocalDate.parse(paramMap.getString("ccEnd")));

        Integer ceIdx = saveEmployee(paramMap);
        CompanyEmployee ce = CompanyEmployee.builder().ceIdx(ceIdx).build();
        contract.setCompanyEmployee(ce);

        String ccFilePath = Common.uploadFilePath(ccFile, "company/contract/", AdminBucket.SECRET);
        contract.setCcFile(ccFilePath);
        contract.setCcFileName(ccFile.getOriginalFilename());

        if (!StringUtils.hasText(paramMap.getString("comIdx"))) {
            throw new MsgException("상호법인명을 선택해주세요.");
        }
        if (StringUtils.hasText(paramMap.getString("uIdx"))) {
            throw new MsgException("내부담당자를 선택해주세요.");
        }
        Company company = Company.builder().comIdx(paramMap.getInt("comIdx")).build();
        User user = User.builder().uIdx(paramMap.getInt("uIdx")).build();
        contract.setCompany(company);
        contract.setUser(user);

        companyContractRepo.save(contract);

        return contract.getCcIdx();
    }

    @Transactional
    public Integer updateContract(int ccIdx, ParamMap paramMap, MultipartFile ccFile) {
        CompanyContract oldContract = companyContractRepo.findById(ccIdx).orElseThrow(() -> new MsgException("존재하지 않는 계약입니다."));

        if (StringUtils.hasText(paramMap.getString("uIdx"))) {
            throw new MsgException("내부담당자를 선택해주세요.");
        }
        paramMap.put("user", User.builder().uIdx(paramMap.getInt("uIdx")).build());
        CompanyContract newContract = paramMap.mapParam(CompanyContract.class);
        newContract.setCcEnd(LocalDate.parse(paramMap.getString("ccEnd")));
        newContract.notUpdate(oldContract);

        CompanyEmployee companyEmployee = oldContract.getCompanyEmployee();
        companyEmployee.ceAllUpdate(paramMap.mapParam(CompanyEmployee.class));
        newContract.setCompanyEmployee(companyEmployee);

        if (ccFile.getSize() != 0) {
            String ccFilePath = Common.uploadFilePath(ccFile, "company/contract/", AdminBucket.SECRET);
            newContract.setCcFile(ccFilePath);
            newContract.setCcFileName(ccFile.getOriginalFilename());
        } else {
            newContract.setCcFile(newContract.getCcFileOri());
            newContract.setCcFileName(newContract.getCcFileNameOri());
        }

        oldContract.setCcState(CcState.STOP);
        CompanyContract savedContract = companyContractRepo.save(newContract);
        return savedContract.getCcIdx();
    }

    /**카테고리별 입점사 수수료 등록*/
    @Transactional
    public void saveFee(ParamMap paramMap) {
        // 이미 등록되어있는 해당 입점사의 중분류 수수료가 있는지 확인 후 에러처리
        Company company = Company.builder().comIdx(paramMap.getInt("comIdx")).build();
        Category category = Category.builder().cateIdx(paramMap.getInt("cateIdx")).build();
        if (companyFeeRepo.findFeeByCompanyAndCategoryAndCfState(company, category, 'Y') != null) {
            throw new MsgException("해당 카테고리에 관한 수수료는 이미 설정되어 있습니다.");
        }
        
        CompanyFee fee = paramMap.mapParam(CompanyFee.class);
        fee.setCfState('Y');
        fee.setCategory(category);
        fee.setCompany(company);

        companyFeeRepo.save(fee);
    }

    /**카테고리별 입점사 수수료 수정**/
    @Transactional
    public void updateFee(Integer cfIdx, ParamMap paramMap) {

        CompanyFee oldFee = companyFeeRepo.findById(cfIdx).orElseThrow(() -> new MsgException("존재하지 않는 거래처입니다."));

        CompanyFee newFee = paramMap.mapParam(CompanyFee.class);
        newFee.setCompany(oldFee.getCompany());
        newFee.setCategory(oldFee.getCategory());
        newFee.setCfState('Y');
        companyFeeRepo.save(newFee);

        oldFee.setCfState('N');
    }

    /**매입처보증보험 등록**/
    @Transactional
    public Integer saveInsurance(ParamMap paramMap, MultipartFile ciFile) {
        CompanyInsurance insurance = paramMap.mapParam(CompanyInsurance.class);
        insurance.setCiStartDate(LocalDate.parse(paramMap.getString("ciStartDate")));
        insurance.setCiEndDate(LocalDate.parse(paramMap.getString("ciEndDate")));

        if (!StringUtils.hasText(paramMap.getString("comIdx"))) {
            throw new MsgException("거래처를 입력해주세요.");
        }
        Company company = Company.builder().comIdx(paramMap.getInt("comIdx")).build();
        insurance.setCompany(company);

        String ciFilePath = "";
        String ciFileName = "";
        if (!ciFile.isEmpty()) {
            ciFilePath = Common.uploadFilePath(ciFile, "company/insurance/", AdminBucket.SECRET);
            ciFileName = ciFile.getOriginalFilename();
        }
        insurance.setCiFile(ciFilePath);
        insurance.setCiFileName(ciFileName);

        CompanyInsurance savedInsurance = companyInsuranceRepo.save(insurance);

        return savedInsurance.getCiIdx();
    }

    /**매입처보증보험 수정**/
    @Transactional
    public void updateInsurance(Integer ciIdx, ParamMap paramMap, MultipartFile ciFile) {
        CompanyInsurance insurance = companyInsuranceRepo.findById(ciIdx).orElseThrow(() -> new MsgException("존재하지 않는 매입처보증보험입니다."));

        if (!ciFile.isEmpty()) {
            String ciFilePath = Common.uploadFilePath(ciFile, "company/insurance/", AdminBucket.SECRET);
            insurance.setCiFile(ciFilePath);
            insurance.setCiFileName(ciFile.getOriginalFilename());
        }

        CompanyInsurance insuranceParam = paramMap.mapParam(CompanyInsurance.class);
        insuranceParam.setCiEndDate(LocalDate.parse(paramMap.getString("ciEndDate")));
        insurance.updateInsurance(insuranceParam);
    }

}

package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final ContractRepo contractRepo;
    private final InsuranceRepo insuranceRepo;
    private final FeeRepo feeRepo;
    private final CompanyEmployeeRepo companyEmployeeRepo;

    /** 거래처 등록 기능 */
    @Transactional
    public Integer saveCompany(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        System.out.println("paramMap = " + paramMap);

        // employee, address
        Integer ceIdx = saveEmployee(paramMap);
        CompanyEmployee companyEmployee = CompanyEmployee.builder().ceIdx(ceIdx).build();
        Address address = paramMap.mapParam(Address.class);
        Company company = paramMap.mapParam(Company.class);
        company.setCompanyEmployee(companyEmployee);
        company.setAddress(address);
        System.out.println("company = " + company);

        // 파일업로드
        String comNumFilePath = Common.uploadFilePath(comNumFile, "company/com_num/", AdminBucket.SECRET);
        String comAccountFilePath = Common.uploadFilePath(comAccountFile, "company/com_account/", AdminBucket.SECRET);

        company.setComNumFile(comNumFilePath);
        company.setComNumFileName(comNumFile.getOriginalFilename());
        company.setComAccountFile(comAccountFilePath);
        company.setComAccountFileName(comAccountFile.getOriginalFilename());

        // 구매담당자
        if (StringUtils.hasText(paramMap.getString("uIdx"))) {
            User user = User.builder().uIdx(Integer.parseInt(paramMap.getString("uIdx"))).build();
            company.setUser(user);
        }

        Company savedCompany = companyRepo.saveAndFlush(company);

        return savedCompany.getComIdx();
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
        companyEmployee.ceAllUpdate(paramMap.getMap());

        // company update
        Address address = paramMap.mapParam(Address.class);
        Company companyParam = paramMap.mapParam(Company.class);

        if (StringUtils.hasText(paramMap.getString("uIdx"))) {
            User user = User.builder().uIdx(Integer.parseInt(paramMap.getString("uIdx"))).build();
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
    public void saveContract(ParamMap paramMap, MultipartFile ccFile) {
        Contract contract = paramMap.mapParam(Contract.class);

        String ccFilePath = Common.uploadFilePath(ccFile, "company/contract/", AdminBucket.SECRET);
        contract.setCcFile(ccFilePath);
        contract.setCcFileName(ccFile.getOriginalFilename());

        String period = paramMap.getMap().get("ccPeriod").toString();
        String[] period_arr = period.split(" ~ ");

        contract.setCcStart(period_arr[0]);
        contract.setCcEnd(period_arr[1]);

        contractRepo.save(contract);
    }

    @Transactional
    public void updateContract(int ccIdx, ParamMap paramMap, MultipartFile ccFile) {
        Contract contract = paramMap.mapParam(Contract.class);
        contract.setCcIdx(ccIdx);

        if (ccFile.getSize() != 0) {
            String ccFilePath = Common.uploadFilePath(ccFile, "company/contract/", AdminBucket.SECRET);
            contract.setCcFile(ccFilePath);
            contract.setCcFileName(ccFile.getOriginalFilename());
        } else {
            contract.setCcFile(contract.getCcFileOri());
            contract.setCcFileName(contract.getCcFileNameOri());
        }

        contractRepo.save(contract);
    }

    /**카테고리별 입점사 수수료 수정**/
    @Transactional
    public void updateFee(Integer cfIdx, ParamMap paramMap) {

        Fee oldFee = feeRepo.findById(cfIdx).orElseThrow(() -> new MsgException("존재하지 않는 거래처입니다."));

        Fee newFee = paramMap.mapParam(Fee.class);
        newFee.setCompany(oldFee.getCompany());
        newFee.setCategory(oldFee.getCategory());
        newFee.setCfState('Y');
        feeRepo.save(newFee);

        oldFee.setCfState('N');
    }

    /**매입처보증보험 등록**/
    @Transactional
    public Integer saveInsurance(ParamMap paramMap, MultipartFile ciFile) {
        paramMap.put("ciStartDate", LocalDate.parse(paramMap.getString("ciStartDate")));
        paramMap.put("ciEndDate", LocalDate.parse(paramMap.getString("ciEndDate")));
        Insurance insurance = paramMap.mapParam(Insurance.class);

        if (!StringUtils.hasText(paramMap.getString("comIdx"))) {
            throw new MsgException("거래처를 입력해주세요.");
        }

        Company company = Company.builder().comIdx(Integer.parseInt(paramMap.getString("comIdx"))).build();
        insurance.setCompany(company);

        String ciFilePath = "";
        String ciFileName = "";
        if (!ciFile.isEmpty()) {
            ciFilePath = Common.uploadFilePath(ciFile, "company/insurance/", AdminBucket.SECRET);
            ciFileName = ciFile.getOriginalFilename();
        }
        insurance.setCiFile(ciFilePath);
        insurance.setCiFileName(ciFileName);

        Insurance savedInsurance = insuranceRepo.save(insurance);

        return savedInsurance.getCiIdx();
    }

    /**매입처보증보험 수정**/
    @Transactional
    public void updateInsurance(Integer ciIdx, ParamMap paramMap) {
        Insurance insurance = insuranceRepo.findInsuranceByCiIdx(ciIdx);
        Insurance insuranceParam = paramMap.mapParam(Insurance.class);
        insurance.updateInsurance(insuranceParam);
    }

}

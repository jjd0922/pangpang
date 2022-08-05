package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.CompanyEmployee;
import com.newper.entity.Contract;
import com.newper.entity.Insurance;
import com.newper.entity.common.Address;
import com.newper.mapper.CompanyMapper;
import com.newper.repository.CompanyRepo;
import com.newper.repository.ContractRepo;
import com.newper.repository.InsuranceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;
    private final CompanyMapper companyMapper;
    private final ContractRepo contractRepo;
    private final InsuranceRepo insuranceRepo;

    /** 거래처 등록 기능 */
    @Transactional
    public Integer saveCompany(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        System.out.println("paramMap = " + paramMap);

        Address address = paramMap.mapParam(Address.class);
        Company company = paramMap.mapParam(Company.class);
        company.setAddress(address);

        String comNumFilePath = Common.uploadFilePath(comNumFile, "company/com_num/", AdminBucket.SECRET);


        String comAccountFilePath = Common.uploadFilePath(comAccountFile, "company/com_account/", AdminBucket.SECRET);


        company.setComNumFile(comNumFilePath);
        company.setComNumFileName(comNumFile.getOriginalFilename());
        company.setComAccountFile(comAccountFilePath);
        company.setComAccountFileName(comNumFile.getOriginalFilename());

        Company savedCompany = companyRepo.saveAndFlush(company);

        return savedCompany.getComIdx();
    }

    @Transactional
    public void updateCompany(Integer comIdx, ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        Company company = companyRepo.findCompanyByComIdx(comIdx);

        // 담당자(CompanyEmployee) update
        CompanyEmployee companyEmployee = company.getCompanyEmployee();
        companyEmployee.ceAllUpdate(paramMap.getMap());

        // company update
        Address address = paramMap.mapParam(Address.class);
        Company companyParam = paramMap.mapParam(Company.class);

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

    /**매입처보증보험 등록**/
    @Transactional
    public Integer saveInsurance(ParamMap paramMap) {
        Insurance insurance = paramMap.mapParam(Insurance.class);
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

package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.ComState;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.CompanyEmployee;
import com.newper.entity.common.Address;
import com.newper.exception.MsgException;
import com.newper.repository.CompanyRepo;
import com.newper.storage.NewperStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepo companyRepo;

    /** 거래처 등록 기능 */
    @Transactional
    public Boolean saveCompany(ParamMap paramMap, MultipartFile comNumFile, MultipartFile comAccountFile) {
        System.out.println("paramMap = " + paramMap);

        Address address = paramMap.mapParam(Address.class);
        Company company = paramMap.mapParam(Company.class);
        company.setAddress(address);

        String comNumFilePath = "";
        String comAccountFilePath = "";

        if(comNumFile.getSize()!=0){
            comNumFilePath = Common.uploadFilePath(comNumFile, "company/com_num/");
        }

        if(comAccountFile.getSize()!=0){
            comAccountFilePath = Common.uploadFilePath(comAccountFile, "company/com_account/");
        }


        System.out.println(comNumFilePath);
        company.setComNumFile(comNumFilePath);
        company.setComAccountFile(comAccountFilePath);
        System.out.println("파일 업로드 완료");
        companyRepo.saveAndFlush(company);

        return true;
    }


}

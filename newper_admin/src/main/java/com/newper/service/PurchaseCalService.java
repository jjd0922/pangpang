package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.Calculate;
import com.newper.entity.CalculateSetting;
import com.newper.entity.Company;
import com.newper.entity.TemplateForm;
import com.newper.exception.MsgException;
import com.newper.repository.CalculateSettingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PurchaseCalService {

    private final CalculateSettingRepo calculateSettingRepo;

    @Transactional
    public Integer saveVendor(ParamMap paramMap) {
        System.out.println("vendor: " + paramMap.getMap().entrySet());
        CalculateSetting calculateSetting = paramMap.mapParam(CalculateSetting.class);
        Company company = Company
                .builder()
                .comIdx(Integer.parseInt(paramMap.get("COM_IDX").toString()))
                .build();
        calculateSetting.setCompany(company);

        try {
            int check = Integer.parseInt(paramMap.get("COM_IDX").toString());
        } catch (NumberFormatException nfe) {
            throw new MsgException("거래처(판매처)를 선택해 주세요");
        }

        String csType = paramMap.getString("csType");
        if (csType == null || csType.equals("")) {
            throw new MsgException("타입을 선택해주세요");
        }

//        숫자 = try catech
//        문자열 = if null || ""


        calculateSettingRepo.save(calculateSetting);
        return calculateSetting.getCsIdx();
    }
    /**벤더정산 팝업삭제*/
    @Transactional
    public void deleteVendor(Integer cs_idx){
        calculateSettingRepo.deleteById(cs_idx);
    }

    /**
     * 벤더정산 업데이트
     */
    @Transactional
    public Integer updateVendor(ParamMap paramMap,Integer csIdx){
        CalculateSetting  cal = calculateSettingRepo.findById(csIdx).get();
        CalculateSetting calculateSetting = paramMap.mapParam(CalculateSetting.class);
        Company company = paramMap.mapParam(Company.class);

        System.out.println("cal.getCsType() = " + cal.getCsType());
        System.out.println("cal.getCsDeliveryCal() = " + cal.getCsDeliveryCal());

      /*  cal.setCsCal(calculateSetting.getCsCal());*/
        cal.setCompany(company);
        cal.setCsPrice(calculateSetting.getCsPrice());
        cal.setCsDeliveryCost(calculateSetting.getCsDeliveryCost());
        cal.setCsOrder(calculateSetting.getCsOrder());
        cal.setCsFee(calculateSetting.getCsFee());
        cal.setCsRealPrice(calculateSetting.getCsRealPrice());
        cal.setCsProduct(calculateSetting.getCsProduct());
        cal.setCsMemo(calculateSetting.getCsMemo());

        calculateSettingRepo.save(cal);
        return cal.getCsIdx();
    }
}

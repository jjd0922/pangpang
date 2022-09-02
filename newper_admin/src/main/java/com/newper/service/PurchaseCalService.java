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

        try {
            String createdDate = paramMap.getString("createdDate");
            calculateSetting.setCreatedDate(calculateSetting.getCreatedDate());
        } catch (NumberFormatException nfe) {
            throw new MsgException("날짜를 입력해 주세요");
        }

        try {
            String csType = paramMap.getString("cs_type");
            calculateSetting.setCsType(csType);
        } catch (NumberFormatException nfe) {
            throw new MsgException("타입을 선택해주세요");
        }
        try {
            String csDeliveryCal = paramMap.getString("cs_delivery_cal");
            calculateSetting.setCsDeliveryCal(csDeliveryCal);
        } catch (NumberFormatException nfe) {

            throw new MsgException("배송비별도정산 타입을 선택해주세요");
        }
        try {
            String csOrder = paramMap.getString("cs_order");
            calculateSetting.setCsOrder(csOrder);
        } catch (NumberFormatException nfe) {

            throw new MsgException("주문번호기준열을 입력해주세요.");
        }
        try {
            String csProduct = paramMap.getString("cs_product");
            calculateSetting.setCsProduct(csProduct);
        } catch (NumberFormatException nfe) {

            throw new MsgException("상품번호기준열을 입력해주세요.");
        }
        try {
            String csPrice = paramMap.getString("cs_price");
            calculateSetting.setCsPrice(csPrice);
        } catch (NumberFormatException nfe) {

            throw new MsgException("판매번호기준열을 입력해주세요.");
        }
        try {
            String csDeliveryCost = paramMap.getString("cs_delivery_cost");
            calculateSetting.setCsDeliveryCost(csDeliveryCost);
        } catch (NumberFormatException nfe) {

            throw new MsgException("배송비기준열을 입력해주세요.");
        }
        try {
            String csFee = paramMap.getString("cs_fee");
            calculateSetting.setCsFee(csFee);
        } catch (NumberFormatException nfe) {

            throw new MsgException("수수료기준열을 입력해주세요.");
        }
        try {
            String csRealPrice = paramMap.getString("cs_real_price");
            calculateSetting.setCsRealPrice(csRealPrice);
        } catch (NumberFormatException nfe) {

            throw new MsgException("정산금액기준열을 입력해주세요.");
        }

        calculateSettingRepo.save(calculateSetting);
        return calculateSetting.getCsIdx();
    }




}

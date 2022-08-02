package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.repository.EstimateProductRepo;
import com.newper.repository.EstimateRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.ArrayList;


@RequiredArgsConstructor
@Service
public class PoService {
    private final EstimateRepo estimateRepo;
    private final EstimateProductRepo estimateProductRepo;


    /** 견적서(po_estimate), 견적서-상품 관계테이블(po_estimate_product) 생성 */
    @Transactional
    public void saveEstimate(ParamMap paramMap, MultipartFile peEstimateFile) {
        System.out.println("param: " + paramMap.entrySet());

        // 견적서 생성 & 수정
        Estimate estimate = paramMap.mapParam(Estimate.class);
        Company company = paramMap.mapParam(Company.class);
        estimate.setCompany(company);

        LocalTime now = LocalTime.now();
        estimate.setPeCode(now.toString());

        String period = paramMap.getMap().get("pePeriodDate").toString();
        String[] period_arr = period.split(" ~ ");

        estimate.setPePeriodStart(period_arr[0]);
        estimate.setPePeriodEnd(period_arr[1]);

        String peEstimateFilePath = Common.uploadFilePath(peEstimateFile, "po/estimate/", AdminBucket.SECRET);
        estimate.setPeEstimateFile(peEstimateFilePath);

        estimateRepo.save(estimate);

        // 견적서-상품 관계테이블 생성
        EstimateProduct estimateProduct = paramMap.mapParam(EstimateProduct.class);
        estimateProduct.setEstimate(estimate);

        String pIdx = paramMap.getMap().get("pIdx").toString();
        String[] pIdx_arr = pIdx.split(", ");

        String purchase_count = paramMap.getMap().get("purchase_count").toString();
        String purchase_cost = paramMap.getMap().get("purchase_cost").toString();

        String[] purchase_count_arr = purchase_count.split(", ");
        String[] purchase_cost_arr = purchase_cost.split(", ");

        for (int i = 0; i < pIdx_arr.length; i++) {
            Product product = paramMap.mapParam(Product.class);
            product.setPIdx(Integer.getInteger(pIdx_arr[i]));
            estimateProduct.setProduct(product);

            estimateProduct.setPepCost(Integer.parseInt(purchase_cost_arr[i]));
            estimateProduct.setPepCount(Integer.parseInt(purchase_count_arr[i]));

            estimateProductRepo.save(estimateProduct);
        }
    }
}


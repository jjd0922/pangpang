package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class PoService {
    private final PoMapper poMapper;
    private final PoRepo poRepo;
    private final PoProductRepo poProductRepo;
    private final EstimateRepo estimateRepo;
    private final CompanyRepo companyRepo;
    private final EstimateProductRepo estimateProductRepo;
    private final WarehouseRepo warehouseRepo;

    /** 발주(po) 생성 */
    public Integer savePo(ParamMap paramMap, MultipartFile poFile) {
        Po po = paramMap.mapParam(Po.class);

        int buyerIdx = paramMap.getInt("comIdx_buy", "매입처 선택 부탁드립니다");
        po.setCompany(companyRepo.getReferenceById(buyerIdx));

        String comIdx_sell = paramMap.getString("comIdx_sell").replaceAll("[^0-9]","");
        if(StringUtils.hasText(comIdx_sell)){
            po.setCompany_sell(companyRepo.getReferenceById(Integer.parseInt(comIdx_sell)));
        }

        String whIdx = paramMap.getString("whIdx").replaceAll("[^0-9]","");
        if(StringUtils.hasText(whIdx)){
            po.setWarehouse(warehouseRepo.getReferenceById(Integer.parseInt(whIdx)));
        }


        if (poFile.getSize() == 0) {
            System.out.println("발주 품의 첨부파일 필수인지 확인 필요");
            po.setPoFile("");
            po.setPoFileName("");
//            throw new MsgException("파일을 첨부해 주세요");
        }else{
            String poFilePath = Common.uploadFilePath(poFile, "po/po/", AdminBucket.SECRET);
            po.setPoFile(poFilePath);
            po.setPoFileName(poFile.getOriginalFilename());
        }

        poRepo.save(po);

        Common.changeArr(paramMap, "poProductCost");
        Common.changeArr(paramMap, "poProductSellPrice");
        Common.changeArr(paramMap, "poProductProcess");
        Common.changeArr(paramMap, "poProductFix");
        Common.changeArr(paramMap, "poProductPaint");
        Common.changeArr(paramMap, "poProductProfitTarget");
        Common.changeArr(paramMap, "poProductCount");
        Common.changeArr(paramMap, "poProduct");
        String [] ppCost = (String[]) paramMap.getMap().get("poProductCost");
        String [] ppSellPrice = (String[]) paramMap.getMap().get("poProductSellPrice");
        String [] ppProcessCost = (String[]) paramMap.getMap().get("poProductProcess");
        String [] ppFixCost = (String[]) paramMap.getMap().get("poProductFix");
        String [] ppPaintCost = (String[]) paramMap.getMap().get("poProductPaint");
        String [] ppProfitTarget = (String[]) paramMap.getMap().get("poProductProfitTarget");
        String [] ppCount = (String[]) paramMap.getMap().get("poProductCount");
        String [] pIdx = (String[]) paramMap.getMap().get("poProduct");


        for (int i = 0; i < ppCost.length; i++) {
            PoProduct poProduct = paramMap.mapParam(PoProduct.class);
            poProduct.setPo(po);

            Product product = paramMap.mapParam(Product.class);
            product.setPIdx((int) Long.parseLong(pIdx[i]));
            poProduct.setProduct(product);

            poProduct.setPpCost(Integer.parseInt(ppCost[i]));
            poProduct.setPpSellPrice(Integer.parseInt(ppSellPrice[i]));
            poProduct.setPpProcessCost(Integer.parseInt(ppProcessCost[i]));
            poProduct.setPpFixCost(Integer.parseInt(ppFixCost[i]));
            poProduct.setPpPaintCost(Integer.parseInt(ppPaintCost[i]));
            poProduct.setPpProfitTarget(Integer.parseInt(ppProfitTarget[i]));
            poProduct.setPpCount(Integer.parseInt(ppCount[i]));

            poProductRepo.save(poProduct);
        }


        return po.getPoIdx();
    }

    /** 견적서(po_estimate), 견적서-상품 관계테이블(po_estimate_product) 생성 */
    @Transactional
    public Integer saveEstimate(ParamMap paramMap, MultipartFile peFile) {
        // 견적서 생성 & 수정
        Estimate estimate = paramMap.mapParam(Estimate.class);
        Company company = paramMap.mapParam(Company.class);
        estimate.setCompany(company);

        LocalTime now = LocalTime.now();
        estimate.setPeCode(now.toString());

        String period = paramMap.getMap().get("pePeriod").toString();
        String[] period_arr = period.split(" ~ ");

        estimate.setPeStart(period_arr[0]);
        estimate.setPeEnd(period_arr[1]);

        String peFilePath = Common.uploadFilePath(peFile, "po/estimate/", AdminBucket.SECRET);
        estimate.setPeFile(peFilePath);
        estimate.setPeFileName(peFile.getOriginalFilename());

        estimateRepo.save(estimate);

        // 견적서-상품 관계테이블 생성
        Common.changeArr(paramMap, "pIdxs");
        Common.changeArr(paramMap, "purchase_count");
        Common.changeArr(paramMap, "sell_price");

        String[] pIdx = (String[]) paramMap.getMap().get("pIdxs");
        String[] pepCount = (String[]) paramMap.getMap().get("purchase_count");
        String[] pepCost = (String[]) paramMap.getMap().get("sell_price");

        for (int i = 0; i < pIdx.length; i++) {
            EstimateProduct estimateProduct = paramMap.mapParam(EstimateProduct.class);
            estimateProduct.setEstimate(estimate);

            Product product = paramMap.mapParam(Product.class);
            product.setPIdx((int) Long.parseLong(pIdx[i]));
            estimateProduct.setProduct(product);

            estimateProduct.setPepCost(Integer.parseInt(pepCost[i]));
            estimateProduct.setPepCount(Integer.parseInt(pepCount[i]));

            estimateProductRepo.save(estimateProduct);
        }

        return estimate.getPeIdx();
    }

    public Optional<Estimate> findById(long peIdx) {
        Integer peIdx_integer = (int) (long) peIdx;
        Optional<Estimate> estimate = estimateRepo.findById(peIdx_integer);
        return estimate;
    }

    public List<Map<String, Object>> selectEstimateProduct(Integer peIdx) {
        return poMapper.selectEstimateProduct(peIdx);
    }

    public void updateEstimate(ParamMap paramMap, MultipartFile peFile, Integer peIdx) {
        // 견적서 생성 & 수정
        Estimate estimate = paramMap.mapParam(Estimate.class);
        estimate.setPeIdx(peIdx);

        Company company = paramMap.mapParam(Company.class);
        estimate.setCompany(company);

        String period = paramMap.getMap().get("pePeriod").toString();
        String[] period_arr = period.split(" ~ ");

        estimate.setPeStart(period_arr[0]);
        estimate.setPeEnd(period_arr[1]);

        if (peFile.getSize() != 0) {
            String peFilePath = Common.uploadFilePath(peFile, "po/estimate/", AdminBucket.SECRET);
            estimate.setPeFile(peFilePath);
            estimate.setPeFileName(peFile.getOriginalFilename());
        } else {
            estimate.setPeFile(estimate.getPeFileOri());
            estimate.setPeFileName(estimate.getPeFileNameOri());
        }

        estimateRepo.save(estimate);

        // 견적서-상품 관계테이블 생성
        List<EstimateProduct> estimateProducts = estimateProductRepo.findEstimateProductByEstimate_PeIdx(peIdx);
        for (int i = 0; i < estimateProducts.size(); i++) {
            estimateProductRepo.deleteById(estimateProducts.get(i).getPepIdx());
        }

        Common.changeArr(paramMap, "pIdxs");
        Common.changeArr(paramMap, "purchase_count");
        Common.changeArr(paramMap, "sell_price");

        String[] pIdx = (String[]) paramMap.getMap().get("pIdxs");
        String[] pepCount = (String[]) paramMap.getMap().get("purchase_count");
        String[] pepCost = (String[]) paramMap.getMap().get("sell_price");

        for (int i = 0; i < pIdx.length; i++) {
            EstimateProduct estimateProduct = paramMap.mapParam(EstimateProduct.class);
            estimateProduct.setEstimate(estimate);

            Product product = paramMap.mapParam(Product.class);
            product.setPIdx((int) Long.parseLong(pIdx[i]));
            estimateProduct.setProduct(product);

            estimateProduct.setPepCost(Integer.parseInt(pepCost[i]));
            estimateProduct.setPepCount(Integer.parseInt(pepCount[i]));

            estimateProductRepo.save(estimateProduct);
        }
    }


}


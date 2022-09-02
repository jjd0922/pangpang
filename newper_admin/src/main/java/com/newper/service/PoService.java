package com.newper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.PoState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.*;


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
    private final SpecListRepo specListRepo;
    private final SpecRepo specRepo;
    private final SpecItemRepo specItemRepo;
    private final SpecMapper specMapper;
    private final CompanyContractRepo companyContractRepo;



    /** 발주(po) 생성 */
    @Transactional
    public Integer savePo(ParamMap paramMap, MultipartFile poFile) {
        Po po = paramMap.mapParam(Po.class);
        System.out.println("po: " + paramMap.getMap().entrySet());

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

        String ccIdx = paramMap.getString("ccIdx").replaceAll("[^0-9]","");
        if(StringUtils.hasText(ccIdx)){
            po.setContract(companyContractRepo.getReferenceById(Integer.parseInt(ccIdx)));
        }


        if (poFile == null || poFile.isEmpty()) {
            po.setPoFile("");
            po.setPoFileName("");
        }else{
            String poFilePath = Common.uploadFilePath(poFile, "po/po/", AdminBucket.SECRET);
            po.setPoFile(poFilePath);
            po.setPoFileName(poFile.getOriginalFilename());
        }

        po.setPoState(PoState.WAITING);
        poRepo.save(po);

        List<Long> ppCost = paramMap.getListLong("poProductCost");
        List<Long> ppSellPrice = paramMap.getListLong("poProductSellPrice");
        List<Long> ppProcessCost = paramMap.getListLong("poProductProcess");
        List<Long> ppFixCost = paramMap.getListLong("poProductFix");
        List<Long> ppPaintCost = paramMap.getListLong("poProductPaint");
        List<Long> ppProfitTarget = paramMap.getListLong("poProductProfitTarget");
        List<Long> ppCount = paramMap.getListLong("poProductCount");
        List<Long> pIdx = paramMap.getListLong("poProduct");
        List<String> ppOption1 = paramMap.getList("poProductOption1");
        List<String> ppOption2 = paramMap.getList("poProductOption2");
        List<String> ppOption3 = paramMap.getList("poProductOption3");
        List<String> ppMemo = paramMap.getList("poProductMemo");
        List<String> ppFixMemo = paramMap.getList("poProductFixMemo");
        List<String> ppPaintMemo = paramMap.getList("poProductPaintMemo");
        List<String> ppProcessMemo = paramMap.getList("poProductProcessMemo");
        List<String> poProductSpec = paramMap.getList("poProductSpec");
        List<String> poProductSpec2 = paramMap.getList("poProductSpec2");


        for (int i = 0; i < pIdx.size(); i++) {
            PoProduct poProduct = paramMap.mapParam(PoProduct.class);
            poProduct.setPo(po);

            Product product = paramMap.mapParam(Product.class);
            product.setPIdx((int) Long.parseLong(pIdx.get(i).toString()));
            poProduct.setProduct(product);

            Spec spec_buy = specRepo.findSpecBySpecConfirm(poProductSpec.get(i));
            if (spec_buy == null) {
                spec_buy = Spec.builder()
                        .specConfirm(poProductSpec.get(i))
                        .specLookup(poProductSpec.get(i))
                        .build();
                specRepo.save(spec_buy);

                String spec = poProductSpec.get(i);
                String[] specArr = spec.split("/");
                for (int j = 0; j < specArr.length; j++) {
                    String speclName = specArr[j].split(":")[0];
                    String speclValue = specArr[j].split(":")[1];

                    SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(speclName, speclValue);

                    if (specList == null) {
                        specList = SpecList
                                .builder()
                                .speclValue(speclValue)
                                .speclName(speclName)
                                .build();
                        specListRepo.save(specList);
                    }

                    Map<String, Object> specParam = new HashMap<>();
                    specParam.put("SPEC_IDX", spec_buy.getSpecIdx());
                    specParam.put("SPECL_IDX", specList.getSpeclIdx());
                    specParam.put("SPECI_ORDER", j);
                    specMapper.insertSpecItem(specParam);

//                    SpecItem specItem = SpecItem.builder()
//                                        .spec(spec_buy)
//                                        .specList(specList)
//                                        .specIOrder(j)
//                                        .build();
//                    specItemRepo.save(specItem);
                }


            }
            poProduct.setSpec(spec_buy);

            Spec spec_sell = specRepo.findSpecBySpecConfirm(poProductSpec2.get(i));
            if (spec_sell == null) {
                spec_sell = Spec.builder()
                        .specConfirm(poProductSpec2.get(i))
                        .specLookup(poProductSpec2.get(i))
                        .build();

                specRepo.save(spec_sell);

                String spec = poProductSpec.get(i);
                String[] specArr = spec.split("/");
                for (int j = 0; j < specArr.length; j++) {
                    String speclName = specArr[j].split(":")[0];
                    String speclValue = specArr[j].split(":")[1];

                    SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(speclName, speclValue);

                    if (specList == null) {
                        specList = SpecList
                                .builder()
                                .speclValue(speclValue)
                                .speclName(speclName)
                                .build();
                        specListRepo.save(specList);
                    }

                    Map<String, Object> specParam = new HashMap<>();
                    specParam.put("SPEC_IDX", spec_sell.getSpecIdx());
                    specParam.put("SPECL_IDX", specList.getSpeclIdx());
                    specParam.put("SPECI_ORDER", j);
                    specMapper.insertSpecItem(specParam);

//                    SpecItem specItem = SpecItem.builder()
//                            .spec(spec_sell)
//                            .specList(specList)
//                            .specIOrder(j)
//                            .build();
//                    specItemRepo.save(specItem);
                }


            }
            poProduct.setSpec2(spec_sell);


            String ppOption = "[";
            if (!ppOption1.get(i).equals("")) {
                String [] option1 = ppOption1.get(i).split(":");
                ppOption += "{\"title\":" + "\"" + option1[0] + "\"" + ",\"values\":" + "\"" + option1[1] + "\"" + "}";
            }

            if (!ppOption2.get(i).equals("")) {
                String [] option2 = ppOption2.get(i).split(":");
                ppOption += "{\"title\":" + "\"" + option2[0] + "\"" + ",\"values\":" + "\"" + option2[1] + "\"" + "}";
            }

            if (!ppOption3.get(i).equals("")) {
                String [] option3 = ppOption3.get(i).split(":");
                ppOption += "{\"title\":" + "\"" + option3[0] + "\"" + ",\"values\":" + "\"" + option3[1] + "\"" + "}";
            }

            ppOption += "]";
            ppOption = ppOption.replace("}{", "},{");

            poProduct.setPpOption(ppOption);
            poProduct.setPpCost(ppCost.get(i).intValue());
            poProduct.setPpSellPrice((int) Long.parseLong(ppSellPrice.get(i).toString()));
            poProduct.setPpProcessCost((int) Long.parseLong(ppProcessCost.get(i).toString()));
            poProduct.setPpFixCost((int) Long.parseLong(ppFixCost.get(i).toString()));
            poProduct.setPpPaintCost((int) Long.parseLong(ppPaintCost.get(i).toString()));
            poProduct.setPpProfitTarget((int) Long.parseLong(ppProfitTarget.get(i).toString()));
            poProduct.setPpCount((int) Long.parseLong(ppCount.get(i).toString()));
            poProduct.setPpMemo(ppMemo.get(i));
            poProduct.setPpPaintMemo(ppPaintMemo.get(i));
            poProduct.setPpFixMemo(ppFixMemo.get(i));
            poProduct.setPpProcessMemo(ppProcessMemo.get(i));
            poProductRepo.save(poProduct);
        }


        return po.getPoIdx();
    }

    /** 발주품의 수정 */
    @Transactional
    public void updatePo(long poIdx, ParamMap paramMap, MultipartFile poFile) {
        Po po = paramMap.mapParam(Po.class);
        System.out.println("po: " + paramMap.getMap().entrySet());

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


        if (poFile == null || poFile.isEmpty()) {
            po.setPoFile(paramMap.get("poFileOri").toString());
            po.setPoFileName(paramMap.get("poFileNameOri").toString());
        }else{
            String poFilePath = Common.uploadFilePath(poFile, "po/po/", AdminBucket.SECRET);
            po.setPoFile(poFilePath);
            po.setPoFileName(poFile.getOriginalFilename());
        }

        poRepo.save(po);


        List<PoProduct> poProducts = poProductRepo.findPoProductByPo_PoIdx((int) poIdx);
        for (int i = 0; i < poProducts.size(); i++) {
            poProductRepo.deleteById(poProducts.get(i).getPpIdx());
        }


        List<Long> ppCost = paramMap.getListLong("poProductCost");
        List<Long> ppSellPrice = paramMap.getListLong("poProductSellPrice");
        List<Long> ppProcessCost = paramMap.getListLong("poProductProcess");
        List<Long> ppFixCost = paramMap.getListLong("poProductFix");
        List<Long> ppPaintCost = paramMap.getListLong("poProductPaint");
        List<Long> ppProfitTarget = paramMap.getListLong("poProductProfitTarget");
        List<Long> ppCount = paramMap.getListLong("poProductCount");
        List<Long> pIdx = paramMap.getListLong("poProduct");
        List<String> ppOption1 = paramMap.getList("poProductOption1");
        List<String> ppOption2 = paramMap.getList("poProductOption2");
        List<String> ppOption3 = paramMap.getList("poProductOption3");
        List<String> ppMemo = paramMap.getList("poProductMemo");
        List<String> ppFixMemo = paramMap.getList("poProductFixMemo");
        List<String> ppPaintMemo = paramMap.getList("poProductPaintMemo");
        List<String> ppProcessMemo = paramMap.getList("poProductProcessMemo");
        List<String> poProductSpec = paramMap.getList("poProductSpec");
        List<String> poProductSpec2 = paramMap.getList("poProductSpec2");


        for (int i = 0; i < pIdx.size(); i++) {
            PoProduct poProduct = paramMap.mapParam(PoProduct.class);
            poProduct.setPo(po);

            Product product = paramMap.mapParam(Product.class);
            product.setPIdx((int) Long.parseLong(pIdx.get(i).toString()));
            poProduct.setProduct(product);

            Spec spec_buy = specRepo.findSpecBySpecConfirm(poProductSpec.get(i));
            if (spec_buy == null) {
                spec_buy = Spec.builder()
                        .specConfirm(poProductSpec.get(i))
                        .specLookup(poProductSpec.get(i))
                        .build();
                specRepo.save(spec_buy);

                String spec = poProductSpec.get(i);
                String[] specArr = spec.split("/");
                for (int j = 0; j < specArr.length; j++) {
                    String speclName = specArr[j].split(":")[0];
                    String speclValue = specArr[j].split(":")[1];

                    SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(speclName, speclValue);

                    if (specList == null) {
                        specList = SpecList
                                .builder()
                                .speclValue(speclValue)
                                .speclName(speclName)
                                .build();
                        specListRepo.save(specList);
                    }

                    Map<String, Object> specParam = new HashMap<>();
                    specParam.put("SPEC_IDX", spec_buy.getSpecIdx());
                    specParam.put("SPECL_IDX", specList.getSpeclIdx());
                    specParam.put("SPECI_ORDER", j);
                    specMapper.insertSpecItem(specParam);

//                    SpecItem specItem = SpecItem.builder()
//                            .spec(spec_buy)
//                            .specList(specList)
//                            .specIOrder(j)
//                            .build();
//                    specItemRepo.save(specItem);
                }


            }
            poProduct.setSpec(spec_buy);

            Spec spec_sell = specRepo.findSpecBySpecConfirm(poProductSpec2.get(i));
            if (spec_sell == null) {
                spec_sell = Spec.builder()
                        .specConfirm(poProductSpec2.get(i))
                        .specLookup(poProductSpec2.get(i))
                        .build();

                specRepo.save(spec_sell);

                String spec = poProductSpec.get(i);
                String[] specArr = spec.split("/");
                for (int j = 0; j < specArr.length; j++) {
                    String speclName = specArr[j].split(":")[0];
                    String speclValue = specArr[j].split(":")[1];

                    SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(speclName, speclValue);

                    if (specList == null) {
                        specList = SpecList
                                .builder()
                                .speclValue(speclValue)
                                .speclName(speclName)
                                .build();
                        specListRepo.save(specList);
                    }

                    Map<String, Object> specParam = new HashMap<>();
                    specParam.put("SPEC_IDX", spec_sell.getSpecIdx());
                    specParam.put("SPECL_IDX", specList.getSpeclIdx());
                    specParam.put("SPECI_ORDER", j);
                    specMapper.insertSpecItem(specParam);

//                    SpecItem specItem = SpecItem.builder()
//                            .spec(spec_sell)
//                            .specList(specList)
//                            .specIOrder(j)
//                            .build();
//                    specItemRepo.save(specItem);
                }


            }
            poProduct.setSpec2(spec_sell);


            String ppOption = "[";
            if (!ppOption1.get(i).equals("")) {
                String [] option1 = ppOption1.get(i).split(":");
                ppOption += "{\"title\":" + "\"" + option1[0] + "\"" + ",\"values\":" + "\"" + option1[1] + "\"" + "}";
            }

            if (!ppOption2.get(i).equals("")) {
                String [] option2 = ppOption2.get(i).split(":");
                ppOption += "{\"title\":" + "\"" + option2[0] + "\"" + ",\"values\":" + "\"" + option2[1] + "\"" + "}";
            }

            if (!ppOption3.get(i).equals("")) {
                String [] option3 = ppOption3.get(i).split(":");
                ppOption += "{\"title\":" + "\"" + option3[0] + "\"" + ",\"values\":" + "\"" + option3[1] + "\"" + "}";
            }

            ppOption += "]";
            ppOption = ppOption.replace("}{", "},{");

            poProduct.setPpOption(ppOption);
            poProduct.setPpCost(ppCost.get(i).intValue());
            poProduct.setPpSellPrice((int) Long.parseLong(ppSellPrice.get(i).toString()));
            poProduct.setPpProcessCost((int) Long.parseLong(ppProcessCost.get(i).toString()));
            poProduct.setPpFixCost((int) Long.parseLong(ppFixCost.get(i).toString()));
            poProduct.setPpPaintCost((int) Long.parseLong(ppPaintCost.get(i).toString()));
            poProduct.setPpProfitTarget((int) Long.parseLong(ppProfitTarget.get(i).toString()));
            poProduct.setPpCount((int) Long.parseLong(ppCount.get(i).toString()));
            poProduct.setPpMemo(ppMemo.get(i));
            poProduct.setPpPaintMemo(ppPaintMemo.get(i));
            poProduct.setPpFixMemo(ppFixMemo.get(i));
            poProduct.setPpProcessMemo(ppProcessMemo.get(i));
            poProductRepo.save(poProduct);
        }
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

    public List<Map<String, Object>> selectPoProduct(long poIdx) {
        List<Map<String, Object>> product = poMapper.selectPoProductByPoIdx(poIdx);
        return product;
    }

    public void productComp(int poIdx) {
        Po po = poRepo.findPoByPoIdx(poIdx);
        po.setPoState(PoState.APPROVAL);
        poRepo.save(po);
    }
}


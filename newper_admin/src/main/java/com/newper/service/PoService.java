package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
import com.newper.constant.HwState;
import com.newper.constant.PoState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
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
    private final SpecMapper specMapper;
    private final CompanyContractRepo companyContractRepo;
    private final HiworksRepo hiworksRepo;
    private final ProductRepo productRepo;



    /** 발주(po) 생성 */
    @Transactional
    public Integer savePo(ParamMap paramMap, MultipartFile poFile) {
        System.out.println("paramMap = " + paramMap);
        // setDate
        paramMap.parseLocalDate("poSellPayDate");
        paramMap.parseLocalDate("poInDate", "입고예정일을 입력해주세요");
        paramMap.parseLocalDate("poDueDate", "납기일을 입력해주세요");
        paramMap.parseLocalDate("poRefundDate");
        paramMap.parseLocalDate("poAsDate");
        paramMap.parseLocalDate("poTaxMonth", "계산서발행월을 입력해주세요");
        paramMap.parseLocalDate("poPayDate", "지급예정일을 입력해주세요");

        Po po = paramMap.mapParam(Po.class);
        po.setPoState(PoState.WAITING);

        // set many to one 친구들
        po.setCompany(companyRepo.getReferenceById(paramMap.getInt("comIdxBuy", "매입처를 선택해주세요")));
        po.setCompany_sell(companyRepo.getReferenceById(paramMap.getInt("comIdxSell", "판매처를 선택해주세요")));
        po.setWarehouse(warehouseRepo.getReferenceById(paramMap.getInt("whIdx", "입고예정창고를 선택해주세요")));

        String ccIdx = paramMap.getString("ccIdx").replaceAll("[^0-9]", "");
        if (StringUtils.hasText(ccIdx)) {
            po.setContract(companyContractRepo.getReferenceById(Integer.parseInt(ccIdx)));
        }

        // set poFile
        if (poFile == null || poFile.isEmpty()) {
            po.setPoFile("");
            po.setPoFileName("");
        } else {
            String poFilePath = Common.uploadFilePath(poFile, "po/po/", AdminBucket.SECRET);
            po.setPoFile(poFilePath);
            po.setPoFileName(poFile.getOriginalFilename());
        }

        poRepo.save(po);

        // poProduct setting
        int productCnt = paramMap.getInt("cnt");
        int errCnt = 0;
        for (int i = 0; i < productCnt; i++) {
            ParamMap ppParam = new ParamMap();
            try {
                ppParam.put("ppMemo", paramMap.getString("ppMemo_" + i));
                ppParam.put("ppCost", paramMap.replaceComma("ppCost_" + i));
                ppParam.put("ppCount", paramMap.getString("ppCount_" + i));
                ppParam.put("ppProfitTarget", paramMap.onlyNumber("ppProfitTarget_" + i));
                ppParam.put("ppFixMemo", paramMap.getString("ppFixMemo_" + i));
                ppParam.put("ppFixCost", paramMap.replaceComma("ppFixCost_" + i));
                ppParam.put("ppPaintMemo", paramMap.getString("ppPaintMemo_" + i));
                ppParam.put("ppPaintCost", paramMap.replaceComma("ppPaintCost_" + i));
                ppParam.put("ppProcessMemo", paramMap.getString("ppProcessMemo_" + i));
                ppParam.put("ppProcessCost", paramMap.replaceComma("ppProcessCost_" + i));

                PoProduct poProduct = ppParam.mapParam(PoProduct.class);

                // option List setting
                List<Map<String, Object>> optionList = new ArrayList<>();
                for (int j = 1; j <= 3; j++) {
                    Map<String, Object> map = new HashMap<>();
                    if (StringUtils.hasText(paramMap.getString("ppOption" + j + "_" + i))) {
                        map.put("values", paramMap.getString("ppOption" + j + "_" + i));
                        map.put("title", paramMap.getString("title_ppOption" + j + "_" + i));
                        optionList.add(map);
                    }
                }
                poProduct.setPpOption(optionList);

                // 입고예정spec setting
                SpecFinder sf = new SpecFinder(specMapper, specListRepo, specRepo);
                List<String> buySpecNameList = paramMap.getList("buySpeclName_" + i);
                List<String> buySpecValueList = paramMap.getList("buySpeclValue_" + i);
                Spec buySpec = sf.findSpec(buySpecNameList, buySpecValueList);
                poProduct.setSpec(buySpec);

                // 판매예정spec setting
                List<String> sellSpecNameList = paramMap.getList("sellSpeclName_" + i);
                List<String> sellSpecValueList = paramMap.getList("sellSpeclValue_" + i);
                Spec sellSpec = sf.findSpec(sellSpecNameList, sellSpecValueList);
                poProduct.setSpec2(sellSpec);
                poProduct.setPo(po);
                poProduct.setProduct(productRepo.getReferenceById(paramMap.getInt("pIdx_" + i, "유효한 상품이 아닙니다.")));

                poProductRepo.save(poProduct);
            } catch (NullPointerException ne) {
                errCnt++;
                continue;
            } finally {
                if (errCnt == productCnt) {
                    throw new MsgException("상품을 선택해주세요");
                }
            }
        }
        return po.getPoIdx();
    }

    /** 발주품의 수정 */
    @Transactional
    public void updatePo(long poIdx, ParamMap paramMap, MultipartFile poFile) {
        /*Po po = paramMap.mapParam(Po.class);
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
        List<Float> ppProfitTarget = paramMap.getListFloat("poProductProfitTarget");
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

                }


            }
            poProduct.setSpec2(spec_sell);

            List<Map<String, Object>> ppOption = new ArrayList<>();

            if (!ppOption1.get(i).equals("")) {
                Common.putOption(ppOption, ppOption1.get(i));
            }

            if (!ppOption2.get(i).equals("")) {
                Common.putOption(ppOption, ppOption2.get(i));
            }

            if (!ppOption3.get(i).equals("")) {
                Common.putOption(ppOption, ppOption3.get(i));
            }

            poProduct.setPpOption(ppOption);
            poProduct.setPpCost(ppCost.get(i).intValue());
            poProduct.setPpSellPrice(ppSellPrice.get(i).intValue());
            poProduct.setPpProcessCost(ppProcessCost.get(i).intValue());
            poProduct.setPpFixCost(ppFixCost.get(i).intValue());
            poProduct.setPpPaintCost(ppPaintCost.get(i).intValue());
            poProduct.setPpProfitTarget(ppProfitTarget.get(i));
            poProduct.setPpCount(ppCount.get(i).intValue());
            poProduct.setPpMemo(ppMemo.get(i));
            poProduct.setPpPaintMemo(ppPaintMemo.get(i));
            poProduct.setPpFixMemo(ppFixMemo.get(i));
            poProduct.setPpProcessMemo(ppProcessMemo.get(i));
            poProductRepo.save(poProduct);
        }*/
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

    public void productComp(int poIdx) {
        Po po = poRepo.findPoByPoIdx(poIdx);
        po.setPoState(PoState.APPROVAL);
        poRepo.save(po);
    }

    /** 발주 하이웍스 승인 요청 */
    @Transactional
    public String poHiworks(ParamMap paramMap, SessionInfo sessionInfo) {
        User user = User
                .builder()
                .uIdx(sessionInfo.getIdx())
                .build();

        String[] poIdxs = paramMap.get("poIdxs").toString().split(",");
        int count = 0;
        for (int i = 0; i < poIdxs.length; i++) {
            Optional<Po> po = poRepo.findById(Integer.parseInt(poIdxs[i]));

            if (po.get().getHiworks() == null) {
                Hiworks hiworks = Hiworks
                        .builder()
                        .hwState(HwState.REQ)
                        .hwType("PO")
                        .hwReqDate(LocalDate.now())
                        .hwReqTime(LocalTime.now())
                        .hwAprvId("")
                        .user(user)
                        .build();

                hiworksRepo.save(hiworks);

                po.get().setHiworks(hiworks);

                poRepo.save(po.get());
                count++;
            }
        }
        return "총 " + count + "개의 발주건을 승인요청했습니다.";
    }

    /** 발주 하이웍스 승인 요청 응답 (반려 & 승인)*/
    @Transactional
    public void poHiworksResponse(ParamMap paramMap) {
        String[] poIdxs = paramMap.get("poIdxs").toString().split(",");
        for (int i = 0; i < poIdxs.length; i++) {
            Po po = poRepo.findPoByPoIdx(Integer.parseInt(poIdxs[i]));
            if (po.getHiworks() != null) {
                Optional<Hiworks> hiworks = hiworksRepo.findById(po.getHiworks().getHwIdx());
                hiworks.get().setHwAprvDate(LocalDate.now());
                hiworks.get().setHwAprvTime(LocalTime.now());
                hiworks.get().setHwAprvId("TEST");
                hiworks.get().setHwState(HwState.APPROVED);

                hiworksRepo.save(hiworks.get());
                poRepo.save(po);
            }
        }
    }


    /** 발주 삭제 */
    @Transactional
    public String poDelete(ParamMap paramMap) {
        String[] poIdxs = paramMap.get("poIdxs").toString().split(",");
        int count = 0;

        for (int i = 0; i < poIdxs.length; i++) {
            Po po = poRepo.findPoByPoIdx(Integer.parseInt(poIdxs[i]));
            if (po.getHiworks() == null) {
                List<PoProduct> poProducts = poProductRepo.findPoProductByPo_PoIdx(po.getPoIdx());
                for (int j = 0; j < poProducts.size(); j++) {
                    poProductRepo.deleteById(poProducts.get(i).getPpIdx());
                }
                poRepo.deleteById(po.getPoIdx());

                count++;
            }
        }
        return "총 " + count + "개의 발주건을 삭제했습니다.";
    }

    public String poStateUpdate(ParamMap paramMap) {
        String[] poIdxs = paramMap.get("poIdxs").toString().split(",");
        int count = 0;
        String state_msg = "";
        for (int i = 0; i < poIdxs.length; i++) {
            Optional<Po> po = poRepo.findById(Integer.parseInt(poIdxs[i]));
            if (po.get().getPoState().equals(PoState.WAITING)) {
                String state = paramMap.get("state").toString();
                if (state.equals("comp")) {
                    po.get().setPoState(PoState.APPROVAL);
                    state_msg = "완료";
                } else {
                    po.get().setPoState(PoState.CANCEL);
                    state_msg = "취소";
                }

                count++;
                poRepo.save(po.get());
            }
        }
        return "총 " + count + "개의 발주건을 " + state_msg + " 처리했습니다.";

    }

    public void selectReceivedByPo(ParamMap paramMap) {
    }
}


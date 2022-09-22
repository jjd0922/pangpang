package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.component.SessionInfo;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.PoMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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
    private final InGroupRepo inGroupRepo;


    private final GoodsRepo goodsRepo;
    private final PoReceivedRepo poReceivedRepo;
    private final ProcessNeedRepo processNeedRepo;
    private final ProcessSpecRepo processSpecRepo;

    /** 발주(po) 생성 */
    @Transactional
    public Integer savePo(ParamMap paramMap, MultipartFile poFile, SessionInfo sessionInfo) {
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
        int rowCnt = paramMap.getInt("rowCnt"); // 화면에 보이는 테이블 행(상품) 개수
        if (rowCnt == 0) {
            throw new MsgException("상품을 선택해주세요");
        }
        int productCnt = paramMap.getInt("cnt");
        List<PoProduct> poProducts = new ArrayList<>();
        for (int i = 0; i < productCnt; i++) {
            if (!StringUtils.hasText(paramMap.getString("pIdx_" + i))) {
                continue;
            }
            ParamMap ppParam = new ParamMap();
            ppParam.put("ppMemo", paramMap.getString("ppMemo_" + i));
            ppParam.put("ppCost", paramMap.getInt("ppCost_" + i));
            ppParam.put("ppCount", paramMap.getInt("ppCount_" + i));
            ppParam.put("ppProfitTarget", paramMap.getFloatZero("ppProfitTarget_" + i));
            ppParam.put("ppFixMemo", paramMap.getString("ppFixMemo_" + i));
            ppParam.put("ppFixCost", paramMap.getIntZero("ppFixCost_" + i));
            ppParam.put("ppPaintMemo", paramMap.getString("ppPaintMemo_" + i));
            ppParam.put("ppPaintCost", paramMap.getIntZero("ppPaintCost_" + i));
            ppParam.put("ppProcessMemo", paramMap.getString("ppProcessMemo_" + i));
            ppParam.put("ppProcessCost", paramMap.getIntZero("ppProcessCost_" + i));
            ppParam.put("ppSellPrice", paramMap.getInt("ppSellPrice_"+i));

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

            poProducts.add(poProduct);
        }
        poProductRepo.saveAll(poProducts);

        // hiworks setting
//        int hwCnt = 0;
//        if (StringUtils.hasText(paramMap.getString("hwCnt"))) {
//            hwCnt = paramMap.getInt("hwCnt");
//        }
//        List<Map<String,Object>> hiworksList = new ArrayList<>();
//        for (int i = 0; i < hwCnt; i++) {
//            if (!StringUtils.hasText(paramMap.getString("hwAprvId_"+i))) {
//                continue;
//            }
//            Hiworks hiworks = Hiworks.builder()
//                    .hwAprvId(paramMap.getString("hwAprvId_"+i))
//                    .hwType(HwType.valueOf(paramMap.getString("hwType_"+i)))
//                    .hwState(HwState.BEFORE)
//                    .user(User.builder().uIdx(sessionInfo.getIdx()).build())
//                    .hwMemo(paramMap.getString("hwMemo_"+i))
//                    .build();
//            hiworksRepo.save(hiworks);
//
//            Map<String,Object> hiworksMap = new HashMap<>();
//            hiworksMap.put("order", paramMap.getInt("order_"+i));
//            hiworksMap.put("hwIdx", hiworks.getHwIdx());
//            hiworksMap.put("poIdx", po.getPoIdx());
//            hiworksList.add(hiworksMap);
//        }
//        poMapper.insertPoHiworks(hiworksList);

        return po.getPoIdx();
    }

    /** 발주품의 수정 */
    @Transactional
    public void updatePo(Integer poIdx, ParamMap paramMap, MultipartFile poFile, SessionInfo sessionInfo) {
        Po po = poRepo.findById(poIdx).orElseThrow(() -> new MsgException("존재하지 않는 발주품의입니다."));

        Po poParam = paramMap.mapParam(Po.class);
        po.updateAll(poParam);

        // set many to one 친구들
        po.setCompany(companyRepo.getReferenceById(paramMap.getInt("comIdxBuy", "매입처를 선택해주세요")));
        po.setCompany_sell(companyRepo.getReferenceById(paramMap.getInt("comIdxSell", "판매처를 선택해주세요")));
        po.setWarehouse(warehouseRepo.getReferenceById(paramMap.getInt("whIdx", "입고예정창고를 선택해주세요")));

        String ccIdx = paramMap.getString("ccIdx").replaceAll("[^0-9]", "");
        if (StringUtils.hasText(ccIdx)) {
            po.setContract(companyContractRepo.getReferenceById(Integer.parseInt(ccIdx)));
        }

        // set poFile
        if (!(poFile == null || poFile.isEmpty())) {
            String poFilePath = Common.uploadFilePath(poFile, "po/po/", AdminBucket.SECRET);
            po.setPoFile(poFilePath);
            po.setPoFileName(poFile.getOriginalFilename());
        }

        // poProduct 삭제후 다시 setting
        int rowCnt = paramMap.getInt("rowCnt");
        if (rowCnt == 0) {
            throw new MsgException("상품을 선택해주세요");
        }
        List<Integer> ppIdxs = paramMap.getList("ppIdx");
        poMapper.deletePoProductBypoIdx(poIdx,ppIdxs);

        // pp 새로 setting
        int productCnt = 0;
        try {
            productCnt = paramMap.getInt("cnt");
        } catch (NumberFormatException ne) {
        }
        for (int i = 0; i < productCnt; i++) {
            if (StringUtils.hasText(paramMap.getString("ppIdx_"+i))) {
                continue;
            }
            if (!StringUtils.hasText(paramMap.getString("pIdx_" + i))) {
                continue;
            }
            ParamMap ppParam = new ParamMap();
            ppParam.put("ppMemo", paramMap.getString("ppMemo_" + i));
            ppParam.put("ppCost", paramMap.getInt("ppCost_" + i));
            ppParam.put("ppCount", paramMap.getInt("ppCount_" + i));
            ppParam.put("ppProfitTarget", paramMap.getFloatZero("ppProfitTarget_" + i));
            ppParam.put("ppFixMemo", paramMap.getString("ppFixMemo_" + i));
            ppParam.put("ppFixCost", paramMap.getIntZero("ppFixCost_" + i));
            ppParam.put("ppPaintMemo", paramMap.getString("ppPaintMemo_" + i));
            ppParam.put("ppPaintCost", paramMap.getIntZero("ppPaintCost_" + i));
            ppParam.put("ppProcessMemo", paramMap.getString("ppProcessMemo_" + i));
            ppParam.put("ppProcessCost", paramMap.getIntZero("ppProcessCost_" + i));
            ppParam.put("ppSellPrice", paramMap.getInt("ppSellPrice_"+i));

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
        }

        // poHiworks 삭제후 새로 setting
//        poMapper.deletePoHiworksBypoIdx(po.getPoIdx());
//
//        // hiworks setting
//        int hwCnt = 0;
//        if (StringUtils.hasText(paramMap.getString("hwCnt"))) {
//            hwCnt = paramMap.getInt("hwCnt");
//        }
//        List<Map<String,Object>> hiworksList = new ArrayList<>();
//        for (int i = 0; i < hwCnt; i++) {
//            if (!StringUtils.hasText(paramMap.getString("hwAprvId_"+i))) {
//                continue;
//            }
//            Hiworks hiworks = Hiworks.builder()
//                                        .hwAprvId(paramMap.getString("hwAprvId_"+i))
//                                        .hwType(HwType.valueOf(paramMap.getString("hwType_"+i)))
//                                        .hwState(HwState.BEFORE)
//                                        .user(User.builder().uIdx(sessionInfo.getIdx()).build())
//                                        .hwMemo(paramMap.getString("hwMemo_"+i))
//                                    .build();
//            hiworksRepo.save(hiworks);
//
//            Map<String,Object> hiworksMap = new HashMap<>();
//            hiworksMap.put("order", paramMap.getInt("order_"+i));
//            hiworksMap.put("hwIdx", hiworks.getHwIdx());
//            hiworksMap.put("poIdx", po.getPoIdx());
//            hiworksList.add(hiworksMap);
//        }
//        poMapper.insertPoHiworks(hiworksList);
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

    /** 입고등록 자산수령 */
    @Transactional
    public void productComp(int poIdx) {
        Po po = poRepo.getReferenceById(poIdx);
        InGroup inGroup = inGroupRepo.findByPo(po);
        if (inGroup.getIgState().equals(IgState.DONE)) {
            throw new MsgException("이미 완료된 발주 정보 입니다.");
        }

        inGroup.setIgState(IgState.ING);
        inGroupRepo.save(inGroup);
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
}


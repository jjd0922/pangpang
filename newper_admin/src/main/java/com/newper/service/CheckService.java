package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.*;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CheckService {

    private final CheckGroupRepo checkGroupRepo;
    private final ChecksMapper checkMapper;
    private final GoodsMapper goodsMapper;
    private final SpecListRepo specListRepo;
    private final SpecRepo specRepo;
    private final CheckGoodsRepo checkGoodsRepo;
    private final GoodsRepo goodsRepo;
    private final SpecMapper specMapper;
    private final ProcessGroupRepo processGroupRepo;
    private final ProcessNeedRepo processNeedRepo;
    private final ProcessSpecRepo processSpecRepo;
    private final ProcessMapper processMapper;

    /** 검수 그룹 등록*/
    @Transactional
    public void insertCheckGroup(ParamMap paramMap){
        CheckGroup checkGroup = paramMap.mapParam(CheckGroup.class);

        Company company = paramMap.mapParam(Company.class);
        checkGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        checkGroup.setUser(user);

        checkGroupRepo.save(checkGroup);

        GState gState = GState.valueOf(paramMap.getString("gState"));
        CgsType cgsType = CgsType.valueOf(paramMap.getString("cgsType"));


        //check goods
        List<Long> gIdx = paramMap.getListLong("gIdx");

        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = goodsRepo.findById(gIdx.get(i)).get();
            goods.setGState(gState);
            goodsRepo.save(goods);

            CheckGoods checkGoods = CheckGoods
                    .builder()
                    .goods(goods)
                    .checkGroup(checkGroup)
                    .cgsExpectedCost(0)
                    .cgsRealCost(0)
                    .cgsType(cgsType.toString())
                    .cgsCount(0)
                    .build();

            checkGoodsRepo.save(checkGoods);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    /** 입고검수 자산 정보 UPDATE */
    @Transactional
    public void goodsInCheck(ParamMap paramMap, MultipartFile[] gFile) {
        // 자산 상태 체크
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (!goods.getGState().equals(GState.CHECK_NEED)) {
            throw new MsgException("영업검수 요청되어 수정이 불가능합니다.");
        }

        //IMEI 저장
        goods.setGImei(paramMap.getString("gImei"));

        //옵션 세팅
        List<Map<String, Object>> option = new ArrayList<>();
        List<String> optionList = paramMap.getList("productOption");
        Common.putOption(option, optionList);

        goods.setGOption(option);

        //메모 저장
        goods.setGMemo(paramMap.getString("gMemo"));

        Map<String, Object> gJson = goods.getGJson();
        // 자산 이미지 업로드
        if (gFile != null) {
            List<String> file = (List<String>) gJson.get("gFile");
            List<String> fileName = (List<String>) gJson.get("gFileName");

            for (int i = 0; i < gFile.length; i++) {
                file.add(Common.uploadFilePath(gFile[i], "goods/photo/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                fileName.add(gFile[i].getOriginalFilename());
            }

            gJson.put("gFile", file);
            gJson.put("gFileName", fileName);
        }

        // 입고SPEC(확정)
        SpecFinder specFinder = new SpecFinder(specMapper, specListRepo, specRepo);
        List<String> specName = paramMap.getList("specName");
        List<String> inSpec2 = paramMap.getList("inSpec2");
        gJson.put("inSpec2", inSpec2);

        // 자산 상태 변경
        goods.setGState(GState.CHECK);

        // CHECK_GOODS 예상비용 업데이트
        int expectedCost = paramMap.getInt("paintCost") + paramMap.getInt("fixCost") + paramMap.getInt("processCost");
        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getInt("cgsIdx")).get();
        checkGoods.setCgsExpectedCost(expectedCost);
        checkGoodsRepo.save(checkGoods);

        // PROCESS_NEED 생성
        if (paramMap.getInt("paintCost") != 0) {
            ProcessNeed paintNeed = processNeedRepo.findByGoodsAndPnCount(goods, 1);
            if (paintNeed == null) {
                paintNeed = ProcessNeed
                        .builder()
                        .goods(goods)
                        .pnType(PnType.PAINT)
                        .pnContent(paramMap.getString("paintMemo"))
                        .pnCount(1)
                        .pnExpectedCost(paramMap.getInt("paintCost"))
                        .pnRealCost(0)
                        .pnProcess(PnProcess.BEFORE)
                        .pnJson(new HashMap<>())
                        .pnState(PnState.NEED)
                        .pnLast(0)
                        .build();
            }

            paintNeed.setPnContent(paramMap.getString("paintMemo"));
            paintNeed.setPnExpectedCost(paramMap.getInt("paintCost"));

            processNeedRepo.save(paintNeed);
        }

        if (paramMap.getInt("fixCost") != 0) {
            ProcessNeed fixNeed = processNeedRepo.findByGoodsAndPnCount(goods, 1);
            if (fixNeed == null) {
                fixNeed = ProcessNeed
                        .builder()
                        .goods(goods)
                        .pnType(PnType.FIX)
                        .pnCount(1)
                        .pnRealCost(0)
                        .pnProcess(PnProcess.BEFORE)
                        .pnJson(new HashMap<>())
                        .pnState(PnState.NEED)
                        .pnLast(0)
                        .build();
            }
            fixNeed.setPnContent(paramMap.getString("fixMemo"));
            fixNeed.setPnExpectedCost(paramMap.getInt("fixCost"));

            processNeedRepo.save(fixNeed);
        }

        List<Integer> psIdx = new ArrayList<>();
        List<Long> psCost = paramMap.getListLong("psCost");
        if (paramMap.getInt("processCost") != 0) {
            List<String> specValue4 = paramMap.getList("specValue4");
            Map<String ,Object> processMap = new HashMap<>();
            processMap.put("spec4", specFinder.findSpec(specName, specValue4).getSpecIdx());
            processMap.put("cost", paramMap.getListLong("psCost"));

            ProcessNeed processNeed = processNeedRepo.findByGoodsAndPnCount(goods, 1);

            if (processNeed == null) {
                processNeed = ProcessNeed
                        .builder()
                        .goods(goods)
                        .pnType(PnType.PROCESS)
                        .pnCount(1)
                        .pnRealCost(0)
                        .pnProcess(PnProcess.BEFORE)
                        .pnJson(new HashMap<>())
                        .pnState(PnState.NEED)
                        .pnLast(0)
                        .pnJson(processMap)
                        .build();
            }
            processNeed.setPnContent(paramMap.getString("processMemo"));
            processNeed.setPnExpectedCost(paramMap.getInt("processCost"));

            processNeedRepo.save(processNeed);

            List<SpecList> specLists = specFinder.selectSpecList(specName, specValue4);


            for (int i = 0; i < psCost.size(); i++) {
                ProcessSpec processSpec = ProcessSpec
                        .builder()
                        .processNeed(processNeed)
                        .psCost(psCost.get(i).intValue())
                        .specList1(specLists.get(i))
                        .psType("EXPECTED")
                        .build();

                processSpecRepo.save(processSpec);

                psIdx.add(processSpec.getPsIdx());
            }
        }

        gJson.put("psCost", psCost);
        gJson.put("psIdx", psIdx);
        goods.setGJson(gJson);
        goodsRepo.save(goods);
    }

    /** 영업검수 자산 정보 확정 */
    @Transactional
    public void goodsInfoComplete(ParamMap paramMap, MultipartFile[] gFile) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (!goods.getGStockState().equals(GStockState.N)) {
            throw new MsgException("이미 재고인계요청 이상 진행된 자산입니다.");
        }

        GState gState = GState.STOCK;
        Map<String, Object> gJson = goods.getGJson();
        // 자산 이미지 업로드
        if (!gFile[0].getOriginalFilename().equals("")) {
            List<String> file = (List<String>) gJson.get("gFile");
            List<String> fileName = (List<String>) gJson.get("gFileName");

            for (int i = 0; i < gFile.length; i++) {
                file.add(Common.uploadFilePath(gFile[i], "goods/photo/" + goods.getGIdx() + "/", AdminBucket.SECRET));
                fileName.add(gFile[i].getOriginalFilename());
            }

            gJson.put("gFile", file);
            gJson.put("gFileName", fileName);
        }

        goods.setGVendor(paramMap.getString("gVendor"));
        goods.setGRank(GRank.valueOf(paramMap.getString("gRank")));
        goods.setGMemo(paramMap.getString("gMemo"));


        List<String> gOption = paramMap.getList("goodsOption");
        List<Map<String, Object>> optionList = new ArrayList<>();
        for (int i = 0; i < gOption.size(); i++) {
            if (!gOption.get(i).equals("")) {
                Map<String, Object> optionMap = new HashMap<>();
                optionMap.put("title", gOption.get(i).split(":")[0]);
                optionMap.put("value", gOption.get(i).split(":")[1]);
                optionList.add(optionMap);
            }
        }
        goods.setGOption(optionList);

        //판매 확정 SPEC
        SpecFinder specFinder = new SpecFinder(specMapper, specListRepo, specRepo);
        List<String> specName = paramMap.getList("specName");
        List<String> spec5_list = paramMap.getList("spec5");
        Spec spec5 = specFinder.findSpec(specName, spec5_list);
        goods.setSellSpec(spec5);

        // PROCESS_NEED 공정필요 진행여부
        List<Long> pnIdx_list = paramMap.getListLong("pnIdx");
        List<String> pnProcess_list = paramMap.getList("pnProcess");
        String psType = "EXPECTED";
        for (int i = 0; i < pnIdx_list.size(); i++) {
            ProcessNeed processNeed = processNeedRepo.findById(pnIdx_list.get(i).intValue()).get();
            processNeed.setPnProcess(PnProcess.valueOf(pnProcess_list.get(i)));

            if (processNeed.getPnProcess().equals("YES")) {
                gState = GState.PROCESS;
                if (processNeed.getPnType().equals(PnType.PROCESS)) {
                    psType = "CONFIRM";
                }
            }

            processNeedRepo.save(processNeed);
        }


        // 가공예정 SPEC
        List<String> spec4 = paramMap.getList("spec4");
        List<Long> psIdx_list = paramMap.getListLong("psIdx");
        List<Long> psCost_list = paramMap.getListLong("");
        for (int i = 0; i < spec4.size(); i++) {
            ProcessSpec processSpec = processSpecRepo.findById(psIdx_list.get(i).intValue()).get();
            processSpec.setPsCost(psCost_list.get(i).intValue());
            processSpec.setPsType(psType);
            processSpecRepo.save(processSpec);
        }


        gJson.put("pSpec1", spec4);
        goods.setGJson(gJson);
        goods.setGState(gState);
        goodsRepo.save(goods);
    }

    /** 공정필요 데이터 생성 (가공, 수리, 도색) */
    @Transactional
    public void insertProcessGroup(ParamMap paramMap) {
        ProcessGroup processGroup = paramMap.mapParam(ProcessGroup.class);

        Company company = paramMap.mapParam(Company.class);
        processGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        processGroup.setUser(user);

        processGroup.setPgType(paramMap.getString("pgType"));
        processGroup.setPgDoneMemo("");
        processGroup.setPgLastState("");
        processGroupRepo.save(processGroup);

        Map<String, Object> param = new HashMap<>();
        param.put("pnType", paramMap.getString("type"));
        param.put("pnState", "NEED");
        //check goods
        List<Long> gIdx = paramMap.getListLong("gIdx");
        for (int i = 0; i < gIdx.size(); i++) {
            param.put("gIdx", gIdx.get(i));

            ProcessNeed processNeed = processMapper.selectProcessNeedEntity(param);
            if (processNeed == null) {
                throw new MsgException("잘못된 접근입니다.");
            }
            processNeed.setProcessGroup(processGroup);
            processNeed.setPnState(PnState.REQUEST);

            processMapper.updateProcessNeed(processNeed);
//            processNeedRepo.save(processNeed);
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }
}

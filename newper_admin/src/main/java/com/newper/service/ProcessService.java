package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.*;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.ProcessMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProcessService {
    private final ProcessNeedRepo processNeedRepo;
    private final ProcessSpecRepo processSpecRepo;
    private final ProcessMapper processMapper;
    private final GoodsMapper goodsMapper;
    private final GoodsRepo goodsRepo;
    private final ResellRepo resellRepo;
    private final ResellGoodsRepo resellGoodsRepo;
    private final CheckGoodsRepo checkGoodsRepo;
    private final SpecListRepo specListRepo;
    private final GoodsStockRepo goodsStockRepo;
    private final CheckGroupRepo checkGroupRepo;
    private final ProcessGroupRepo processGroupRepo;
    private final GoodsService goodsService;
    private final AfterServiceRepo afterServiceRepo;

    /** 공정 가공 데이터 생성 */
    @Transactional
    public void saveProcessSpec(ParamMap paramMap) {
        ProcessNeed processNeed = processNeedRepo.findById(paramMap.getInt("pnIdx")).get();
        Map<String, Object> pnJson = processNeed.getPnJson();

        List<Long> psIdx = paramMap.getListLong("psIdx");
        List<Long> gsIdx = paramMap.getListLong("gsIdx");
        List<Long> inSpecIdx = paramMap.getListLong("inSpecIdx");
        List<String> pSpec2 = paramMap.getList("pSpec2");
        List<Long> outSpecIdx = paramMap.getListLong("outSpecIdx");
        List<String> outSpecName = paramMap.getList("outSpecName");

        for (int i = 0; i < psIdx.size(); i++) {
            ProcessSpec processSpec = processSpecRepo.findById(psIdx.get(i).intValue()).get();

            if (inSpecIdx.get(i) != 0) {
                processSpec.setSpecList1(specListRepo.getReferenceById(inSpecIdx.get(i).intValue()));
                processSpec.setGoodsStock(goodsStockRepo.getReferenceById(gsIdx.get(i).intValue()));
            } else {
                processSpec.setSpecList1(null);
                processSpec.setGoodsStock(null);
            }

            if (outSpecIdx.get(i) != 0) {
                processSpec.setSpecList2(specListRepo.getReferenceById(outSpecIdx.get(i).intValue()));
            } else {
                processSpec.setSpecList2(null);
            }

            processSpecRepo.save(processSpec);
        }

        pnJson.put("gsIdx", gsIdx);
        pnJson.put("outSpecName", outSpecName);
        pnJson.put("pSpec2", pSpec2);
        processNeedRepo.save(processNeed);
    }

    /** 공정 삭제 내역 */
    @Transactional
    public void deleteProcessSpec(ParamMap paramMap) {
        int pnIdx = paramMap.getInt("pnIdx");

        ProcessNeed processNeed = processNeedRepo.findById(pnIdx).get();
        if (processNeed.getPnState().equals("COMP")) {
            throw new MsgException("해당 공정 내역은 이미 완료된 건입니다.");
        }

        int psIdx = paramMap.getInt("psIdx");
        processSpecRepo.deleteById(psIdx);
    }

    /** 공정결과 업로드 */
    @Transactional
    public void saveProcess(ParamMap paramMap, MultipartFile[] pnFile) {
        // 공정 필요 수정
        ProcessNeed processNeed = processNeedRepo.findByPnIdx(paramMap.getInt("pnIdx"));
        processNeed.setPnContent(paramMap.getString("pnContent"));

        Map<String, Object> pnJson = processNeed.getPnJson();
        if (!pnFile[0].getOriginalFilename().equals("")) {
            List<String> file = (List<String>) pnJson.get("pnFile");
            List<String> fileName = (List<String>) pnJson.get("pnFileName");

            for (int i = 0; i < pnFile.length; i++) {
                file.add(Common.uploadFilePath(pnFile[i], "goods/process/" + processNeed.getPnIdx() + "/", AdminBucket.SECRET));
                fileName.add(pnFile[i].getOriginalFilename());
            }

            pnJson.put("pnFile", file);
            pnJson.put("pnFileName", fileName);
        }
        processNeedRepo.save(processNeed);


        List<Long> psIdx = paramMap.getListLong("psIdx");
        List<Long> psCost = paramMap.getListLong("psCost");
        for (int i = 0; i < psIdx.size(); i++) {
            ProcessSpec processSpec = processSpecRepo.findById(psIdx.get(i).intValue()).get();
            processSpec.setPsCost(psCost.get(i).intValue());
            processSpecRepo.save(processSpec);
        }

        List<Long> gsIdx = paramMap.getListLong("gsIdx");
        List<Long> gsCost = paramMap.getListLong("gsCost");
        for (int i = 0; i < gsIdx.size(); i++) {
            ProcessSpec processSpec = ProcessSpec
                    .builder()
                    .processNeed(processNeed)
                    .goodsStock(goodsStockRepo.getReferenceById(gsIdx.get(i).intValue()))
                    .psCost(gsCost.get(i).intValue())
                    .psType(PsType.CONFIRM)
                    .build();
            processSpecRepo.save(processSpec);
        }


    }

    /** 반품 그룹 생성 */
    @Transactional
    public void resellPop(ParamMap paramMap) {
        Resell resell = paramMap.mapParam(Resell.class);
        Company company = paramMap.mapParam(Company.class);
        resell.setCompany(company);

        resellRepo.save(resell);
        List<String> gIdx = paramMap.getList("gIdx");
        Map<String, Object> resellGoods = new HashMap<>();
        resellGoods.put("gIdxs", gIdx);
        resellGoods.put("rsIdx", resell.getRsIdx());
        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdx.get(i))).get();
            goods.setGState(GState.CANCEL_DONE);
            goodsRepo.save(goods);
        }
        processMapper.insertResellGoods(resellGoods);

        int ggt_idx = paramMap.getInt("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }

    public void resellComp(ParamMap paramMap) {
        List<Long> rgIdx = paramMap.getList("rgIdx");

        for (int i = 0; i < rgIdx.size(); i++) {
            ResellGoods resellGoods = resellGoodsRepo.findByRgIdx(rgIdx.get(i).intValue());
            resellGoods.setRgMemo(paramMap.getString("rsMemo"));
            resellGoods.setRgState("COMP");

            resellGoodsRepo.save(resellGoods);

            Goods goods = Goods
                    .builder()
                    .gIdx(resellGoods.getGoods().getGIdx())
                    .gState(GState.CANCEL_DONE)
                    .build();

            goodsRepo.save(goods);
        }
    }

    /** 검수 내용 업데이트 */
    public void updateCheckGoods(ParamMap paramMap, MultipartFile[] gFile) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        if (goods.getGState().equals(GState.CHECK_REQ)) {
            throw new MsgException("해당건은 이미 영업검수 요청한 자산입니다.");
        }

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

// 도색
        int paintIdx = paramMap.getIntZero("paintIdx");
        String paintContent = paramMap.getString("paintContent");
        int paintCost = paramMap.getIntZero("paintCost");

        if (paintIdx == 0 && !paintContent.replaceAll(" ","").equals("") && paintCost != 0) {
            int pnIdx = this.insertProcessNeed(paramMap, goods, PnType.PAINT);
            paramMap.put("paintIdx", pnIdx);
        } else if (paintIdx != 0){
            updateProcessNeed(paramMap, paintIdx);
        }


        // 수리
        int fixIdx = paramMap.getIntZero("fixIdx");
        String fixContent = paramMap.getString("fixContent");
        int fixCost = paramMap.getIntZero("fixCost");

        if (fixIdx == 0 && !fixContent.replaceAll(" ","").equals("") && fixCost != 0) {
            int pnIdx = this.insertProcessNeed(paramMap, goods, PnType.FIX);
            paramMap.put("fixIdx", pnIdx);
        } else if (fixIdx != 0) {
            updateProcessNeed(paramMap, fixIdx);
        }

        // 가공
        int processIdx = paramMap.getIntZero("processIdx");
        String processContent = paramMap.getString("processContent");
        int processCost = paramMap.getIntZero("processCost");

        if (processIdx == 0 && !processContent.replaceAll(" ","").equals("") && processCost != 0) {
            int pnIdx = this.insertProcessNeed(paramMap, goods, PnType.PROCESS);
            paramMap.put("processIdx", pnIdx);
        } else if (processIdx != 0) {
            updateProcessNeed(paramMap, processIdx);
        }

        goodsRepo.save(goods);
    }

    /** 공정보드 입시그룹 자산 추가 */
    @Transactional
    public void insertTempBarcode(ParamMap paramMap) {
        // 자산 체크
        Goods goods = goodsRepo.findBygBarcode(paramMap.getString("gBarcode"));
        if (goods == null) {
            throw new MsgException("해당 자산 바코드는 없는 자산 입니다.");
        }

        String type = paramMap.getString("type");
        GState gstate = goods.getGState();
        if (type.equals("RE_CHECK")) {
            if (!gstate.equals(GState.RE_CHECK_NEED)) {
                throw new MsgException("해당자산은 재검수요청 할 수 없는 자산 입니다.");
            }
        } else if (type.equals("IN_CHECK")) {
            if (!gstate.equals(GState.CHECK_NEED)) {
                throw new MsgException("해당자산은 입고검수요청 할 수 없는 자산 입니다.");
            }
        }  if (type.equals("PROCESS") || type.equals("FIX") || type.equals("PAINT")) {
            // 해당 자산의 필요 공정중 진행 합의된 자산들 SELECT
            int check = processMapper.checkProcessNeed(goods.getGIdx(), type);
            if (!gstate.equals(GState.PROCESS) || check != 1) {
                throw new MsgException("해당자산은 공정요청 할 수 없는 자산 입니다.");
            }
        } else if (type.equals("RESELL")) {
            if (!gstate.equals(GState.CANCEL_REQ)) {
                throw new MsgException("해당자산은 반품 할 수 없는 자산 입니다.");
            }
        }

        processMapper.insertGoodsTemp(goods.getGIdx(), paramMap.getInt("ggtIdx"));
    }

    /** 재검수 완료 */
    @Transactional
    public void checkDone(ParamMap paramMap) {
        CheckGroup checkGroup = checkGroupRepo.getById(paramMap.getInt("cgIdx"));

        CgType cgType = CgType.valueOf(paramMap.getString("cgType"));
        String[] gIdx = paramMap.getString("gIdx").split(",");
        for (int i = 0; i < gIdx.length; i++) {
            // 해당 자산에 다른 공정이 있는지 확인
            int check = processMapper.checkProcessNeedOtherByGoods(Long.parseLong(gIdx[i]));
            Goods goods = goodsRepo.findById(Long.parseLong(gIdx[i])).get();
            Map<String ,Object> gJson = goods.getGJson();
            goodsService.updateGoodsBy(gJson);

            if (cgType.equals(CgType.IN)) { // 입고검수
//                goods.setGState(GState.CHECK_DONE);
            } else if (cgType.equals(CgType.RE)) { // 재검수
                goods.setGState(GState.STOCK);
                goods.setGStockState(GStockState.STOCK_REQ);
                gJson.put("reProcess", "N");
            } else { // 출고전 검수
//                goods.setGState(GState.OUT_CHECK_DONE);
            }



            CheckGoods checkGoods = checkGoodsRepo.findByGoodsAndCheckGroup(goods, checkGroup);
            checkGoods.setCgsState(CgsState.FINISH);
            checkGoodsRepo.save(checkGoods);

        }

        // 해당 공정에 완료처리 되지 않은 자산이 있는지 확인
        int check = processMapper.checkProcessNeedOtherByChecks(paramMap.getInt("cgIdx"));
        if (check == 0) {
            checkGroup.setCgState(CgState.FINISH);
            checkGroupRepo.save(checkGroup);
        }
    }


    /** 출고전 검수 */
    @Transactional
    public void uploadOutReport(ParamMap paramMap, MultipartFile[] gFile) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();

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

        // CHECK_GOODS 예상비용 업데이트
        int expectedCost = paramMap.getIntZero("paintCost") + paramMap.getIntZero("fixCost") + paramMap.getIntZero("processCost");
//        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getIntZero("cgsIdx")).get();
//        checkGoods.setCgsExpectedCost(expectedCost);
//        checkGoodsRepo.save(checkGoods);

        // 공정 비용이 없음
        if (expectedCost == 0) {
            goods.setGState(GState.STOCK);
            goods.setGStockState(GStockState.STOCK_REQ);
        } else {
//            this.insertProcessNeed(goods, paramMap, PnType.PAINT);
//            this.insertProcessNeed(goods, paramMap, PnType.FIX);
//            this.insertProcessNeed(goods, paramMap, PnType.PROCESS);

//            goods.setGState(GState.OUT_CHECK_DONE);
        }

        goodsRepo.save(goods);
    }


    /** 재검수 작업완료(입고) 처리 */
//    @Transactional
    public void reCheckDone(ParamMap paramMap) {
        String[] gIdx = paramMap.getString("gIdx").split(",");
        int cgIdx = paramMap.getIntZero("cgIdx");
        CheckGroup checkGroup = checkGroupRepo.findById(cgIdx).get();

        for (int i = 0; i < gIdx.length; i++) {
            int goods_check = processMapper.checkProcessNeedOtherByGoods(Long.parseLong(gIdx[i]));
            Goods goods = goodsRepo.findById(Long.parseLong(gIdx[i])).get();

            if (goods_check == 0) {
                goods.setGState(GState.STOCK);
                goods.setGStockState(GStockState.STOCK_REQ);
            } else {
//                goods.setGState(GState.RE_CHECK_DONE);
            }

            CheckGoods checkGoods = checkGoodsRepo.findByGoodsAndCheckGroup(goods, checkGroup);
            checkGoods.setCgsState(CgsState.FINISH);
            goodsRepo.save(goods);
        }
        int check = processMapper.checkProcessNeedOtherByChecks(cgIdx);
        if (check == 0) {
            checkGroup.setCgState(CgState.FINISH);
            checkGroupRepo.save(checkGroup);
        }
    }


    /** 검수 리포트 등록 */
    @Transactional
    public void updateCheckReport(ParamMap paramMap, MultipartFile[] gFile) {
        // 1.자산정보 업데이트
        Goods goods = goodsRepo.findById(Long.parseLong(paramMap.getString("gIdx"))).get();
        goods.setGImei(paramMap.getString("gImei"));
        goods.setGSerial(paramMap.getString("gSerial"));

        // 2. 예상비용 업데이트
        // 도색
        int paintIdx = paramMap.getInt("paintIdx");
        String paintContent = paramMap.getString("paintContent");
        int paintCost = paramMap.getIntZero("paintCost");

        if (paintIdx == 0 && !paintContent.replaceAll(" ","").equals("") && paintCost != 0) {
            int pnIdx = this.insertProcessNeed(paramMap, goods, PnType.PAINT);
            paramMap.put("paintIdx", pnIdx);
        } else if (paintIdx != 0){
            updateProcessNeed(paramMap, paintIdx);
        }


        // 수리
        int fixIdx = paramMap.getInt("fixIdx");
        String fixContent = paramMap.getString("fixContent");
        int fixCost = paramMap.getIntZero("fixCost");

        if (fixIdx == 0 && !fixContent.replaceAll(" ","").equals("") && fixCost != 0) {
            int pnIdx = this.insertProcessNeed(paramMap, goods, PnType.FIX);
            paramMap.put("fixIdx", pnIdx);
        } else if (fixIdx != 0) {
            updateProcessNeed(paramMap, fixIdx);
        }

        // 가공
        int processIdx = paramMap.getInt("processIdx");
        String processContent = paramMap.getString("processContent");
        int processCost = paramMap.getIntZero("processCost");

        if (processIdx == 0 && !processContent.replaceAll(" ","").equals("") && processCost != 0) {
            int pnIdx = this.insertProcessNeed(paramMap, goods, PnType.PROCESS);
            paramMap.put("processIdx", pnIdx);
        } else if (processIdx != 0) {
            updateProcessNeed(paramMap, processIdx);
        }

        // 상품 SPEC 정보
        Map<String, Object> gJson = goods.getGJson();
        this.processJsonSetting(paramMap, gJson, PnType.PAINT);
        this.processJsonSetting(paramMap, gJson, PnType.FIX);
        this.processJsonSetting(paramMap, gJson, PnType.PROCESS);


        // 옵션
        List<Map<String, Object>> option = new ArrayList<>();
        List<String> optionList = paramMap.getList("productOption");
        Common.putOption(option, optionList);
        goods.setGOption(option);

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

        // 메모
        goods.setGMemo(paramMap.getString("gMemo"));
        goods.setGJson(gJson);

        goodsRepo.save(goods);
    }

    /** 공정필요 생성 */
    @Transactional
    public int insertProcessNeed(ParamMap paramMap, Goods goods, PnType pnType) {
        String type = pnType.name().toLowerCase();
        int pnCount = processMapper.selectProcessNeedCount(goods.getGIdx(), pnType.name());
        Map<String, Object> pnJson = new HashMap<>();
        pnJson.put(type+"Content", paramMap.getString(type+"Content"));
        pnJson.put(type+"Cost", paramMap.getString(type+"Cost"));

        ProcessNeed processNeed = ProcessNeed
                .builder()
                .goods(goods)
                .pnType(pnType)
                .pnJson(pnJson)
                .pnCount(pnCount + 1)
                .pnContent(paramMap.getString(type+"Content"))
                .pnExpectedCost(paramMap.getInt(type+"Cost"))
                .pnRealCost(0)
                .pnProcess(PnProcess.BEFORE)
                .pnState(PnState.NEED)
                .pnLast(pnCount)
                .build();

        if (paramMap.getString(type+"Process") != null) {
            processNeed.setPnProcess(PnProcess.valueOf(paramMap.getString(type+"Process")));
            if (paramMap.get("pSpec1") != null) {
                List<String> pSpec1 = paramMap.getList("pSpec1");
                pnJson.put("pSpec1", pSpec1);
            }


            if (paramMap.get("pSpec2") != null) {
                List<String> pSpec2 = paramMap.getList("pSpec2");
                pnJson.put("pSpec2", pSpec2);
            }
        }

        processNeedRepo.save(processNeed);
        paramMap.put(type+"Idx", processNeed.getPnIdx());
        if (paramMap.get("cgsIdx") != null) {
            CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getInt("cgsIdx")).get();
            checkGoods.setCgsCost(paramMap.getIntZero("cgsCost"));
            Map<String, Object> cgsJson = checkGoods.getCgsJson();

            processNeed.setCheckGoods(checkGoods);
            this.processJsonSetting(paramMap, cgsJson, pnType);

            checkGoods.setCgsJson(cgsJson);
            checkGoodsRepo.save(checkGoods);
        }

        if (pnType.equals(PnType.PROCESS)) {
            pnJson.put("psIdx", this.insertProcessSpec(paramMap, processNeed, pnJson));
        }


        processNeedRepo.save(processNeed);
        return processNeed.getPnIdx();
    }

    /** 공정필요 수정 */
    @Transactional
    public void updateProcessNeed(ParamMap paramMap, int pnIdx) {
        if (pnIdx != 0) {
            ProcessNeed processNeed = processNeedRepo.findById(pnIdx).get();
            String type = processNeed.getPnType().name().toLowerCase();
            String content = paramMap.getString(type + "Content");
            int cost = paramMap.getIntZero(type + "Cost");
            processNeed.setPnContent(content);
            processNeed.setPnExpectedCost(cost);

            Map<String, Object> pnJson = processNeed.getPnJson();
            pnJson.put(type+"Content", content);
            pnJson.put(type+"Cost", cost);

            if (paramMap.get(type + "Process") != null) {
                processNeed.setPnProcess(PnProcess.valueOf(paramMap.getString(type + "Process")));
                pnJson.put(type+"Process", PnProcess.valueOf(paramMap.getString(type + "Process")).name());

            }

            if (paramMap.get("pgIdx") != null) {
                processNeed.setProcessGroup(processGroupRepo.getReferenceById(paramMap.getInt("pgIdx")));
            }

            if (processNeed.getPnType().equals(PnType.PROCESS)) {
                this.updateProcessSpec(paramMap, processNeed, pnJson);
            }

            processNeedRepo.save(processNeed);
        }
    }

    private void updateProcessSpec(ParamMap paramMap, ProcessNeed processNeed, Map<String, Object> pnJson) {
        List<ProcessSpec> processSpecList = processSpecRepo.findByProcessNeed(processNeed);
        List<Long> psCost = paramMap.getListLong("psCost");
        List<String> specName = paramMap.getList("specName");
        List<String> pSpec1 = paramMap.getList("pSpec1");
        pnJson.put("pSpec1", pSpec1);
        pnJson.put("psCost", psCost);
        for (int i = 0; i < processSpecList.size(); i++) {
            processSpecList.get(i).setPsCost(psCost.get(i).intValue());

            if (pSpec1.get(i).equals("")) {
                processSpecList.get(i).setSpecList1(null);
            } else {
                SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(specName.get(i), pSpec1.get(i));
                processSpecList.get(i).setSpecList1(specList);
            }
        }
    }

    /** 공정 (스펙, 용역) 등록 */
    private List<Integer> insertProcessSpec(ParamMap paramMap, ProcessNeed processNeed, Map<String, Object> pnJson) {
        List<String> pSpec1 = paramMap.getList("pSpec1");
        List<String> pSpec2 = paramMap.getList("pSpec2");
        List<Long> psCost = paramMap.getListLong("psCost");
        List<String> specName = paramMap.getList("specName");
        List<Integer> psIdx = new ArrayList<>();
        for (int i = 0; i < pSpec1.size(); i++) {
            if (psCost.get(i) != 0 && !pSpec1.get(i).replace(" ", "").equals("")) {
                SpecList specList = specListRepo.findSpecListBySpeclNameAndSpeclValue(specName.get(i), pSpec1.get(i));
                ProcessSpec processSpec = ProcessSpec
                        .builder()
                        .processNeed(processNeed)
                        .psCost(psCost.get(i).intValue())
                        .psType(PsType.EXPECTED)
                        .specList1(specList)
                        .specList2(null)
                        .goodsStock(null)
                        .build();
                processSpecRepo.save(processSpec);
                psIdx.add(processSpec.getPsIdx());
            }
        }

        pnJson.put("psCost", psCost);
        pnJson.put("pSpec1", pSpec1);
        pnJson.put("pSpec2", pSpec2);

        return psIdx;
    }

    /** 검수그룹 취소 */
    @Transactional
    public void inCheckCancel(ParamMap paramMap) {
        int cgIdx = paramMap.getInt("cgIdx");
        CheckGroup checkGroup = checkGroupRepo.findById(cgIdx).get();
        if (!checkGroup.getCgState().equals(CgState.BEFORE)) {
            throw new MsgException("이미 진행중인 검수건이라 취소하실수 없습니다.");
        }

        List<Map<String, Object>> goodsList = goodsMapper.selectGoodsByCheckGroup(cgIdx);
        List<Long> gIdx = new ArrayList<>();
        for (int i = 0; i < goodsList.size(); i++) {
            gIdx.add(Long.parseLong(goodsList.get(i).get("G_IDX").toString()));
        }

        goodsMapper.updateGoodsState(gIdx, GState.CHECK_NEED.name());

        checkGroupRepo.deleteById(cgIdx);
    }

    /** 공정관련 정보 json setting */
    public void processJsonSetting(ParamMap paramMap, Map<String, Object> json, PnType pnType) {
        String type = pnType.name().toLowerCase();
        json.put(type+"Content", paramMap.getString(type+"Content"));
        json.put(type+"Cost", paramMap.getString(type+"Cost"));
        json.put(type+"Idx", paramMap.getInt(type+"Idx"));
        List<Long> psCost = paramMap.getListLong("psCost");
        json.put("psCost", psCost);

        if (paramMap.get(type+"Process") != null) {
            json.put(type+"Process", PnProcess.valueOf(paramMap.getString(type + "Process")).name());
        }
    }

    /** 해당 공정 그룹 상태값 변경 */
    @Transactional
    public void updateProcessState(ParamMap paramMap) {
        PnState pnState = PnState.valueOf(paramMap.getString("pnState"));
        String[] pnIdxs = paramMap.getString("pnIdx").split(",");

        for (int i = 0; i < pnIdxs.length; i++) {
            ProcessNeed processNeed = processNeedRepo.findByPnIdx(Integer.parseInt(pnIdxs[i]));

            // 공정 상태변환을 요청한 자산의 공정상태값이 완료일때
            if (processNeed.getPnState().equals(PnState.COMPLETE)) {
                throw new MsgException("해당 자산은 이미 공정이 완료된 자산입니다.");
            }


            // 작업 완료 요청한 자산의 공정상태값이 진행중이 아닐때
            if (processNeed.getPnState().equals(PnState.REQUEST) && pnState.equals(pnState.equals(PnState.COMPLETE))) {
                throw new MsgException("해당 자산은 공정완료 할 수 없는 자산입니다.");
            }

            processNeed.setPnState(pnState);
            Goods goods = processNeed.getGoods();
            Map<String, Object> gJson = goods.getGJson();
            goodsService.updateGoodsBy(gJson);
            gJson.put("reProcess", "Y");
            // 완료처리시
           if (pnState.equals(PnState.COMPLETE)) {
               int check = processMapper.checkProcessNeedOtherByGoods(goods.getGIdx());
               if (check == 0) {
                   goods.setGState(GState.RE_CHECK_NEED);
               } else {
                   goods.setGState(GState.PROCESS);
               }
           }


            goodsRepo.save(goods);
        }
    }

    /** 공정 그룹 완료 체크후 처리 */
    public String checkProcessGroup(ParamMap paramMap) {
        int pgIdx = paramMap.getInt("pgIdx");
        List<ProcessNeed> processNeedList = processNeedRepo.findByProcessGroup(processGroupRepo.findById(pgIdx).get());
        int total = processNeedList.size();
        int end = 0;
        int ing = 0;

        for (int i = 0; i< processNeedList.size(); i++) {
            if (processNeedList.get(i).getPnState().equals(PnState.COMPLETE)) {
                end++;
            } else {
                ing++;
            }
        }
        return "해당 공정그룹의 속해있는 총 " + total + "개의 자산중 " + end +"개의 자산이 완료되었고 " + end +"개의 자산이 미완료 되었습니다.";
    }

    /** AS리포트 등록 **/
    @Transactional
    public void asCheckReport(ParamMap paramMap) {
        AfterService afterService = afterServiceRepo.findById(paramMap.getLong("asIdx")).get();

        if (!afterService.getAsState().equals(AsState.REQUEST)) {
            throw new MsgException("해당 AS 건은 영업검수가 끝난건 입니다.");
        }

        int asCost = paramMap.getIntZero("paintCost") + paramMap.getIntZero("fixCost") + paramMap.getIntZero("processCost");
        afterService.setAsCost(asCost);
        afterServiceRepo.save(afterService);

        if (paramMap.get("asState") != null) {
            afterService.setAsState(AsState.valueOf(paramMap.getString("asState")));
        }

        Goods goods = goodsRepo.findById(Long.parseLong(paramMap.getString("gIdx"))).get();
        goods.setGMemo(paramMap.getString("gMemo"));

        goodsRepo.save(goods);
         // 공정 필요 생성
        saveProcessNeed(paramMap, PnType.PAINT);
        saveProcessNeed(paramMap, PnType.FIX);
        saveProcessNeed(paramMap, PnType.PROCESS);
    }

    @Transactional
    public void saveProcessNeed(ParamMap paramMap, PnType pnType) {
        String type = pnType.name().toLowerCase();
        int idx = paramMap.getIntZero(type+"Idx");
        String content = paramMap.getString(type+"Content");
        int cost = paramMap.getInt(type+"Cost");
        ProcessNeed processNeed;
        if (idx == 0 && !content.replaceAll(" ","").equals("") && cost != 0) {
            int pnCount = processMapper.selectProcessNeedCount(paramMap.getLong("gIdx"), pnType.name());
            processMapper.updateProcessNeedLast(paramMap.getLong("gIdx"), pnType.name());
            processNeed = ProcessNeed
                    .builder()
                    .goods(goodsRepo.getReferenceById(paramMap.getLong("gIdx")))
                    .pnType(pnType)
                    .pnContent(content)
                    .pnCount(pnCount + 1)
                    .pnExpectedCost(cost)
                    .pnRealCost(0)
                    .pnLast(1)
                    .pnJson(new HashMap<>())
                    .pnProcess(PnProcess.BEFORE)
                    .pnState(PnState.NEED)
                    .build();
            processNeedRepo.save(processNeed);


            if (paramMap.get("cgsIdx") != null && paramMap.getIntZero("cgsIdx") != 0) {
                processNeed.setCheckGoods(checkGoodsRepo.getReferenceById(paramMap.getInt("cgsIdx")));
            }

            if (paramMap.get("asIdx") != null && paramMap.getIntZero("asIdx") != 0) {
                processNeed.setAfterService(afterServiceRepo.getReferenceById(paramMap.getLong("asIdx")));
            }

            processNeedRepo.save(processNeed);
        } else {
            processNeed =  processNeedRepo.findById(idx).get();
            processNeed.setPnContent(content);
            processNeed.setPnExpectedCost(cost);
        }

        if (paramMap.get(type+"Process") != null) {
            PnProcess pnProcess = PnProcess.valueOf(paramMap.getString(type+"Process"));
            processNeed.setPnProcess(pnProcess);

            if (pnProcess.equals(PnProcess.Y)) {
                Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
                goods.setGStockState(GStockState.N);
                goods.setGState(GState.PROCESS);
                goodsRepo.save(goods);
            }
        }

        Map<String, Object> pnJson = processNeed.getPnJson();

        List<Long> psCost = paramMap.getListLong("psCost");
        pnJson.put("psCost", psCost);

        if (paramMap.get("pSpec1") != null) {
            List<String> pSpec1 = paramMap.getList("pSpec1");
            pnJson.put("pSpec1", pSpec1);
        }

        if (paramMap.get("pSpec2") != null) {
            List<String> pSpec2 = paramMap.getList("pSpec2");
            pnJson.put("pSpec2", pSpec2);
        }

        processNeed.setPnJson(pnJson);

        if (pnType.equals(PnType.PROCESS)) {
            saveProcessSpec(paramMap, processNeed);
        }

        processNeedRepo.save(processNeed);
    }

    /** 가공 내역 */
    @Transactional
    public void saveProcessSpec(ParamMap paramMap, ProcessNeed processNeed) {
        List<ProcessSpec> processSpecList = processSpecRepo.findByProcessNeed(processNeed);
        List<Long> psCost = paramMap.getListLong("psCost");
        if (processSpecList.size() == 0) {
            for (int i = 0; i < psCost.size(); i++) {
                ProcessSpec processSpec = ProcessSpec
                        .builder()
                        .processNeed(processNeed)
                        .psType(PsType.EXPECTED)
                        .psCost(psCost.get(i).intValue())
                        .build();
                processSpecRepo.save(processSpec);
            }
        } else {
            for (int i = 0; i < processSpecList.size(); i++) {
                processSpecList.get(i).setPsCost(psCost.get(i).intValue());
            }
        }
    }
}

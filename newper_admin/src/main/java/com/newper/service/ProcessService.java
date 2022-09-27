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
import com.newper.util.SpecFinder;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final SpecMapper specMapper;
    private final SpecRepo specRepo;
    private final ChecksMapper checksMapper;
    private final CheckGroupRepo checkGroupRepo;
    private final ProcessGroupRepo processGroupRepo;

    /** 공정 가공 데이터 생성 */
    @Transactional
    public void saveProcessSpec(ParamMap paramMap) {
        ProcessNeed processNeed = processNeedRepo.findById(paramMap.getInt("pnIdx")).get();

        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        Map<String, Object> gJson = goods.getGJson();

        List<String> psIdx = paramMap.getList("psIdx");
        List<String> psCost = paramMap.getList("psCost");
        List<String> changeSpec = paramMap.getList("changeSpecIdx");
        List<String> changeSpecName = paramMap.getList("changeSpecName");
        List<String> removeSpec = paramMap.getList("removeSpecIdx");
        List<String> removeSpecName = paramMap.getList("removeSpecName");
        List<String> gsIdx = paramMap.getList("gsIdx");

        List<String> specName = paramMap.getList("specName");

        Map<String, Object> pnJson = processNeed.getPnJson();
        pnJson.put("psIdx", psIdx);
        pnJson.put("psCost", psCost);
        pnJson.put("changeSpec", changeSpec);
        pnJson.put("changeSpecName", changeSpecName);
        pnJson.put("removeSpec", removeSpec);
        pnJson.put("removeSpecName", removeSpecName);
        pnJson.put("gsIdx", gsIdx);
        processNeed.setPnJson(pnJson);

        SpecFinder specFinder = new SpecFinder(specMapper, specListRepo, specRepo);


        for (int i = 0; i < gsIdx.size(); i++) {
            if (!gsIdx.get(i).equals("")) {
                ProcessSpec processSpec = processSpecRepo.findById(Integer.parseInt(psIdx.get(i))).get();
                if (processSpec == null) {
                    processSpec = ProcessSpec
                            .builder()
                            .processNeed(processNeed)
                            .specList1(specFinder.findSpecList(specName.get(i), changeSpecName.get(i)))
                            .specList2(specFinder.findSpecList(specName.get(i), removeSpecName.get(i)))
                            .goodsStock(goodsStockRepo.getReferenceById(Integer.parseInt(gsIdx.get(i))))
                            .psType(PsType.CONFIRM)
                            .psCost(Integer.parseInt(psCost.get(i)))
                            .build();
                }

                processSpec.setPsCost(Integer.parseInt(psCost.get(i)));
                if (!changeSpec.get(i).equals("")) {
                    processSpec.setSpecList1(specListRepo.getReferenceById(Integer.valueOf(changeSpec.get(i))));
                }

                if (!removeSpec.get(i).equals("")) {
                    processSpec.setSpecList2(specListRepo.getReferenceById(Integer.valueOf(removeSpec.get(i))));
                }

                if (!gsIdx.get(i).equals("")) {
                    GoodsStock goodsStock = goodsStockRepo.getReferenceById(Integer.valueOf(gsIdx.get(i)));
                    processSpec.setGoodsStock(goodsStock);
                }

                processSpecRepo.save(processSpec);
            }
        }

        goods.setGJson(gJson);
        goodsRepo.save(goods);
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
        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getIntZero("cgsIdx")).get();
        checkGoods.setCgsExpectedCost(expectedCost);
        checkGoodsRepo.save(checkGoods);

        this.insertProcessNeed(goods, paramMap, PnType.PAINT);
        this.insertProcessNeed(goods, paramMap, PnType.FIX);
        this.insertProcessNeed(goods, paramMap, PnType.PROCESS);

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
            if (!gstate.equals(GState.RECEIVED)) {
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

    /** 해당 검수그룹 완료 체크 */
    public void checkDone(ParamMap paramMap) {
        int count = checksMapper.selectCheckGroupGoods(paramMap.getMap());
        if (count == 0) {
            int cgIdx = paramMap.getInt("cgIdx");
            CheckGroup checkGroup = checkGroupRepo.findById(cgIdx).get();
            checkGroup.setCgState(CgState.FINISH);
            checkGroupRepo.save(checkGroup);
        }
    }

    /** 공정필요 생성 */
    @Transactional
    public void insertProcessNeed(Goods goods, ParamMap paramMap, PnType pnType) {
        String type = pnType.name();
        if (type.equals("PAINT")) {type = "paint";}
        else if (type.equals("FIX")) {type = "fix";}
        else {type = "process";}

        Map<String, Object> pnJson = new HashMap<>();
        if (paramMap.getIntZero(type+"Cost") != 0) {
            ProcessNeed processNeed;
            if (paramMap.getIntZero(type+"PnIdx") != 0) {
                processNeed = processNeedRepo.findById(paramMap.getIntZero(type+"PnIdx")).get();
                pnJson = processNeed.getPnJson();
            } else  {
                int pnCount = processMapper.selectProcessNeedCount(goods.getGIdx(), pnType.name());
                processNeed = ProcessNeed
                        .builder()
                        .goods(goods)
                        .pnType(pnType)
                        .pnCount(pnCount + 1)
                        .pnRealCost(0)
                        .pnProcess(PnProcess.BEFORE)
                        .pnState(PnState.NEED)
                        .pnLast(0)
                        .build();
            }

            if (paramMap.getString("cgIdx") != null) {
                processNeed.setCheckGroup(checkGroupRepo.findCheckGroupByCgIdx(paramMap.getIntZero("cgIdx")));
            }

            processNeed.setPnContent(paramMap.getString(type+"Content"));
            processNeed.setPnExpectedCost(paramMap.getIntZero(type+"Cost"));
            pnJson.put(type+"Content", paramMap.getString(type+"Content"));
            pnJson.put(type+"Cost", paramMap.getString(type+"Cost"));
            if (paramMap.getString(type+"Process") != null) {
                processNeed.setPnProcess(PnProcess.valueOf(paramMap.getString(type+"Process")));
            }

            if (pnType.equals(PnType.PROCESS)) {
                processNeed.setPnJson(pnJson);
                processNeedRepo.save(processNeed);

                List<Long> psCost = paramMap.getListLong("psCost");
                pnJson.put("psCost", psCost);

                List<Long> psIdx = paramMap.getListLong("psIdx");
                for (int i = 0; i < psIdx.size(); i++) {
                    if (psIdx.get(i) == 0) {
                        ProcessSpec processSpec = ProcessSpec
                                .builder()
                                .processNeed(processNeed)
                                .psCost(psCost.get(i).intValue())
                                .psType(PsType.EXPECTED)
                                .build();
                        processSpecRepo.save(processSpec);

                        psIdx.set(i, Long.valueOf(processSpec.getPsIdx()));
                    } else {
                        ProcessSpec processSpec = processSpecRepo.findById(psIdx.get(i).intValue()).get();
                        processSpec.setPsCost(psCost.get(i).intValue());
                        processSpecRepo.save(processSpec);
                    }
                }
                pnJson.put("psIdx", psIdx);


                if (paramMap.get("pSpec1") != null) {
                    List<String> pSpec1 = paramMap.getList("pSpec1");
                    pnJson.put("pSpec1", pSpec1);
                }

                if (paramMap.get("pSpec2") != null) {
                    List<String> pSpec2 = paramMap.getList("pSpec2");
                    pnJson.put("pSpec2", pSpec2);
                }

                processNeed.setPnJson(pnJson);
            } else {
                processNeed.setPnJson(pnJson);
                processNeedRepo.save(processNeed);
            }
        }
    }

    /** 해당 공정 그룹 상태값 변경 */
    @Transactional
    public void updateProcessState(ParamMap paramMap) {
        String[] pnIdxs = paramMap.getString("pnIdx").split(",");
        for (int i = 0; i < pnIdxs.length; i++) {
            ProcessNeed processNeed = processNeedRepo.findByPnIdx(Integer.parseInt(pnIdxs[i]));

            PnState pnState_ori = processNeed.getPnState();
            PnState pnState = PnState.valueOf(paramMap.getString("pnState"));
            if (pnState.equals(PnState.OUT) || pnState.equals(PnState.HOLD)) {
                if (pnState_ori.equals(PnState.OUT) || pnState_ori.equals(PnState.COMPLETE)) {
                    throw new MsgException("이미 공정출고 되었거나 공정완료된 자산입니다.");
                }

                if (pnState.equals(PnState.HOLD)) {
                    processNeed.setProcessGroup(null);
                }


            } else if (pnState.equals(PnState.COMPLETE)) {
                if (pnState_ori.equals(PnState.COMPLETE)) {
                    throw new MsgException("이미 공정완료된 자산입니다.");
                }
                Goods goods = processNeed.getGoods();
                int check = processMapper.checkProcessNeedOther(goods.getGIdx());
                if (check == 0) {
                    goods.setGState(GState.RE_CHECK_NEED);
                } else {
                    goods.setGState(GState.PROCESS);
                }

                goodsRepo.save(goods);
            }

            processNeed.setPnState(pnState);
            processNeedRepo.save(processNeed);
        }
    }
}

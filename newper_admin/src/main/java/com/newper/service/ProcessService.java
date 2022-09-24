package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GState;
import com.newper.constant.PnState;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.ProcessMapper;
import com.newper.repository.*;
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


    /** 공정 가공 데이터 생성 */
    @Transactional
    public void saveProcessSpec(ParamMap paramMap) {
        Goods goods = goodsRepo.findById(paramMap.getLong("gIdx")).get();
        Map<String, Object> gJson = goods.getGJson();

        List<Long> psIdx = paramMap.getListLong("psIdx");
        List<Long> psCost = paramMap.getListLong("psCost");
        List<String> changeSpec = paramMap.getList("changeSpecIdx");
        List<String> removeSpec = paramMap.getList("removeSpecIdx");
        List<String> gsIdx = paramMap.getList("gsIdx");

        for (int i = 0; i < psIdx.size(); i++) {
            ProcessSpec processSpec = processSpecRepo.findById(psIdx.get(i).intValue()).get();
            processSpec.setPsCost(psCost.get(i).intValue());
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

        gJson.put("pSpec2", paramMap.getList("changeSpecName"));
        gJson.put("changeSpecIdx", paramMap.getList("changeSpecIdx"));
        gJson.put("removeSpecName", paramMap.getList("removeSpecName"));
        gJson.put("removeSpecIdx", paramMap.getList("removeSpecIdx"));
        gJson.put("gsIdx", paramMap.getList("gsIdx"));

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

        if (processNeed.getPnState().equals(PnState.COMPLETE.name())) {
            throw new MsgException("해당 공정은 이미 완료된 건입니다.");
        }

        processNeed.setPnState(PnState.valueOf(paramMap.getString("pnState")));
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
                    .psType("CONFIRM")
                    .build();
            processSpecRepo.save(processSpec);
        }

        // 공정 완료 처리시 자산 상태값 변경
        if (PnState.COMPLETE.name().equals(PnState.valueOf(paramMap.getString("pnState")).name())) {

            Goods goods = processNeed.getGoods();
            int process = processMapper.selectProcessNeedByGoods(goods.getGIdx());
            if (process == 0) {
                goods.setGState(GState.CHECK);
            } else {
                goods.setGState(GState.PROCESS);
            }
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
    public void updateCheckGoods(ParamMap paramMap, MultipartFile[] cgsFile) {
        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getInt("cgsIdx")).get();
        if (cgsFile.length != 0) {
            List<Map<String, Object>> cgsFileList = new ArrayList<>();
            for (int i = 0; i < cgsFile.length; i++) {
                Map<String, Object> cgsFileMap = new HashMap<>();
                String path = Common.uploadFilePath(cgsFile[i], "check/re/" + paramMap.getInt("gIdx") + "/", AdminBucket.SECRET);
                cgsFileMap.put("cgsFile", path);
                cgsFileMap.put("cgsFileName", cgsFile[i].getOriginalFilename());
                cgsFileList.add(cgsFileMap);
            }
        }

        checkGoodsRepo.save(checkGoods);
    }

}

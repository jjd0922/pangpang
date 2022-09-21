package com.newper.service;

import com.newper.component.AdminBucket;
import com.newper.component.Common;
import com.newper.constant.GState;
import com.newper.constant.PsType;
import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.exception.MsgException;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.mapper.ProcessMapper;
import com.newper.mapper.SpecMapper;
import com.newper.repository.*;
import com.newper.util.SpecFinder;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Proc;
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


    /** 공정 가공 데이터 생성 */
    @Transactional
    public void saveProcessSpec(ParamMap paramMap) {
        System.out.println(paramMap.getMap().entrySet());
        List<Long> speclIdx = paramMap.getListLong("psBomIdx");
        List<Long> gsIdx = paramMap.getListLong("gsIdx");
        List<String> psRemove = paramMap.getList("psRemove");
        ProcessNeed processNeed = paramMap.mapParam((ProcessNeed.class));

        for (int i = 0; i < speclIdx.size(); i++) {
            GoodsStock goodsStock = GoodsStock
                    .builder()
                    .gsIdx(gsIdx.get(i).intValue())
                    .build();

            SpecList specList = SpecList
                    .builder()
                    .speclIdx(speclIdx.get(i).intValue())
                    .build();

            ProcessSpec processSpec = ProcessSpec
                    .builder()
                    .processNeed(processNeed)
                    .specList(specList)
                    .goodsStock(goodsStock)
                    .psRemove(psRemove.get(i))
                    .psType(PsType.CONFIRM)
                    .build();

            processSpecRepo.save(processSpec);
        }

    }

    /** 공정 삭제 내역 */
    @Transactional
    public void deleteProcessSpec(ParamMap paramMap) {
        int pnIdx = paramMap.getInt("pnIdx");

        ProcessNeed processNeed = processNeedRepo.findById(Long.valueOf(pnIdx)).get();
        if (processNeed.getPnState().equals("COMP")) {
            throw new MsgException("해당 공정 내역은 이미 완료된 건입니다.");
        }

        int psIdx = paramMap.getInt("psIdx");
        processSpecRepo.deleteById(psIdx);
    }

    /** 공정결과 업로드 */
    @Transactional
    public void saveProcess(ParamMap paramMap) {
        // 공정 필요 수정
        ProcessNeed processNeed = paramMap.mapParam(ProcessNeed.class);
        processNeedRepo.save(processNeed);

        List<Long> psIdx = paramMap.getListLong("psIdx");
        List<Long> psRealCost = paramMap.getListLong("psRealCost");

        for (int i = 0; i < psIdx.size(); i++) {
            ProcessSpec processSpec = ProcessSpec
                    .builder()
                    .psIdx(psIdx.get(i).intValue())
                    .psRealCost(psRealCost.get(i).intValue())
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
    public void updateCheckGoods(ParamMap paramMap, MultipartFile[] cgsFile) {
        CheckGoods checkGoods = checkGoodsRepo.findById(paramMap.getInt("cgsIdx")).get();
        checkGoods.setCgsPaintMemo(paramMap.getString("paintMemo"));
        checkGoods.setCgsPaintCost(paramMap.getInt("paintCost"));
        checkGoods.setCgsFixMemo(paramMap.getString("fixMemo"));
        checkGoods.setCgsFixCost(paramMap.getInt("fixCost"));
        checkGoods.setCgsProcessMemo(paramMap.getString("processMemo"));
        checkGoods.setCgsProcessCost(paramMap.getInt("processCost"));
        checkGoods.setCgsMemo(paramMap.getString("cgsMemo"));
        if (cgsFile.length != 0) {
            List<Map<String, Object>> cgsFileList = new ArrayList<>();
            for (int i = 0; i < cgsFile.length; i++) {
                Map<String, Object> cgsFileMap = new HashMap<>();
                String path = Common.uploadFilePath(cgsFile[i], "check/re/" + paramMap.getInt("gIdx") + "/", AdminBucket.SECRET);
                cgsFileMap.put("cgsFile", path);
                cgsFileMap.put("cgsFileName", cgsFile[i].getOriginalFilename());
                cgsFileList.add(cgsFileMap);
            }
            checkGoods.setCgsFile(cgsFileList);
        }

        checkGoodsRepo.save(checkGoods);
    }

}

package com.newper.service;

import com.newper.constant.GState;
import com.newper.dto.ParamMap;
import com.newper.entity.Goods;
import com.newper.entity.Location;
import com.newper.exception.MsgException;
import com.newper.mapper.BomMapper;
import com.newper.repository.GoodsRepo;
import com.newper.repository.LocationRepo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepo locationRepo;
    private final GoodsRepo goodsRepo;


    /** 재고인수(적재) 자산상태 적재로 수정 */
    @Transactional
    public void saveStockTake(ParamMap paramMap) {
        int locIdx = paramMap.getInt("locIdx");
        Location location = locationRepo.findById(locIdx).get();

        String[] gIdxs = paramMap.getString("gIdxs").split(",");
        for (int i = 0; i < gIdxs.length; i++) {
            Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
            goods.setLocation(location);
            goods.setGState(GState.STOCK);
            goodsRepo.save(goods);
        }
    }

    /** 재고적재(바코드) 자산상태 적재로 수정 */
    @Transactional
    public void saveStockBarcode(ParamMap paramMap) {
        int locIdx = paramMap.getInt("locIdx");
        Location location = locationRepo.findById(locIdx).get();

        String barcode = paramMap.getString("barcode");
        Goods goods = goodsRepo.findBygBarcode(barcode);

        if (goods == null) {
            throw new MsgException("없는 자산바코드 입니다.");
        }

        if (!goods.getGState().equals(GState.STOCK_REQ)) {
            throw new MsgException("재고인계요청 상태의 자산만 적재할 수 있습니다.");
        }

        goods.setLocation(location);
    }
}

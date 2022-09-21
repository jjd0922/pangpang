package com.newper.service;

import com.newper.constant.GState;
import com.newper.dto.ParamMap;
import com.newper.entity.Goods;
import com.newper.entity.GoodsStock;
import com.newper.entity.Location;
import com.newper.entity.User;
import com.newper.exception.MsgException;
import com.newper.mapper.LocationMapper;
import com.newper.repository.GoodsRepo;
import com.newper.repository.GoodsStockRepo;
import com.newper.repository.LocationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepo locationRepo;
    private final GoodsStockRepo goodsStockRepo;
    private final GoodsRepo goodsRepo;

    private final LocationMapper locationMapper;

    /**
     * 재고인수(적재) 자산상태 적재로 수정
     */
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

    /**
     * 재고적재(바코드) 자산상태 적재로 수정
     */
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


    /**
     * 창고이동등록 자산 바코드 리딩
     */

    @Transactional
    public List<Map<String, Object>> listStockBarcode(ParamMap paramMap) {


        String barcode = paramMap.getString("barcode");
        String[] gIdxs = barcode.substring(1, (barcode.length() - 0)).split(",");
        System.out.println(gIdxs.length);

/*            Goods goods = paramMap.mapParam(Goods.class);

            if (paramMap.getString(goods.getGBarcode()).equals(barcode)){

                return locationMapper.selectListGoodsByLocation(gIdxs);
            }

          */
/*        Goods goods = goodsRepo.findBygBarcode(barcode);
        if (goods == null) {
            throw new MsgException("일치하는 바코드가 없습니다");
        }*/

    /*        for (int i = 0; i < gIdxs.length; i++) {

                System.out.println("gIdxs = " + gIdxs[i]);
            }*/


        return  locationMapper.selectListGoodsByLocation(gIdxs);
    }
}
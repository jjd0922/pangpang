package com.newper.service;

import ch.qos.logback.core.joran.conditional.ElseAction;
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
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
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

        if (!goods.getGState().equals(GState.STOCK)) {
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
        System.out.println(barcode);
        String[] barcodes = barcode.substring(1, (barcode.length() - 0)).split(",");
        System.out.println(barcodes.length);


        Goods goods = goodsRepo.findBygBarcode(barcodes[barcodes.length-1]);


        if (goods==null){
//            barcode=barcode.replace("," + barcodes[barcodes.length - 1], "");
            throw new MsgException("존재하는 바코드가 아닙니다");
        }

        System.out.println(barcode.replace("," + barcode, "")+"/"+barcodes[barcodes.length-1]);


        if(barcode.split(barcodes[barcodes.length-1]).length>1){
            throw new MsgException("리딩된 바코드입니다.");
        }

        return  locationMapper.selectListGoodsByLocation(barcodes);
    }



    /**창고이동 팝업 작업완료 처리*/

    @Transactional
    public Integer changeLocation(ParamMap paramMap){

 /*       Location location = paramMap.mapParam(Location.class);
        Goods goods = paramMap.mapParam(Goods.class);

*/
        String locIdx = String.valueOf(locationRepo.findLocationByLocIdx(Integer.parseInt("locIdx")));
         String locIdxs[] = locIdx.substring(1, (locIdx.length() - 0)).split(",");

        System.out.println("locIdxs = " + locIdxs);





        return paramMap.getInt(locIdx);
    }
 

}
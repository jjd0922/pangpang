package com.newper.service;

import com.newper.constant.GState;
import com.newper.constant.LmState;
import com.newper.dto.ParamMap;
import com.newper.entity.Goods;
import com.newper.entity.Location;
import com.newper.entity.LocationMove;
import com.newper.entity.User;
import com.newper.exception.MsgException;
import com.newper.mapper.LocationMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocationService {

    private final LocationRepo locationRepo;
    private final GoodsStockRepo goodsStockRepo;
    private final GoodsRepo goodsRepo;

    private final UserRepo userRepo;
    private final LocationMoveRepo locationMoveRepo;
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


        Goods goods = goodsRepo.findBygBarcode(barcodes[barcodes.length - 1]);


        if (goods == null) {
//            barcode=barcode.replace("," + barcodes[barcodes.length - 1], "");
            throw new MsgException("존재하는 바코드가 아닙니다");
        }

        System.out.println(barcode.replace("," + barcode, "") + "/" + barcodes[barcodes.length - 1]);


        if (barcode.split(barcodes[barcodes.length - 1]).length > 1) {
            throw new MsgException("리딩된 바코드입니다.");
        }

        return locationMapper.selectListGoodsByLocation(barcodes);
    }


    /**
     * 창고이동 팝업 작업완료 처리
     */

    @Transactional
    public void changeLocation(ParamMap paramMap,int[] locIdx,long[] gIdx) {
        int loc_idx_out = locIdx[0];
        int loc_idx_in = locIdx[1];
        paramMap.put("LM_STATE",LmState.FINISHWORK);
        Location location1 = locationRepo.getReferenceById(loc_idx_out);
        Location location2 = locationRepo.getReferenceById(loc_idx_in);
        User user = userRepo.getReferenceById(1);

        Integer whIdx1 = location1.getWarehouse().getWhIdx();
        Integer whIdx2 = location2.getWarehouse().getWhIdx();




        LocationMove locationMove =
                LocationMove.builder()
                        .build();
        locationMove.setUser(User.builder().build());
        locationMove.setLocation2(location2);
        locationMove.setLocation1(location1);
        locationMove.setLmInDate(LocalDate.now());
        if(whIdx1==whIdx2){
            locationMove.setLmState(LmState.valueOf(paramMap.getString("LM_STATE")));

            System.out.println("locationMove = " + locationMove);
            locationMove.setUser(user);
        }else{
            locationMove.setLmState(LmState.WORKING);
        }
        locationMove.setLmMemo(paramMap.getString("LM_MEMO"));
        locationMoveRepo.saveAndFlush(locationMove);

        long lmIdx = locationMove.getLmIdx();

        for (int i = 0; i < gIdx.length; i++) {
            System.out.println(gIdx[i]);
            Long G_IDX = gIdx[i];
            Goods goods = goodsRepo.getReferenceById(G_IDX);

            goods.setLocation(locationMove.getLocation2());
            /*locationRepo.save(location2);*/
            goodsRepo.save(goods);

        }
        //관계테이블 인서트
       locationMapper.insertLocationMoveGoods(lmIdx,gIdx);
        System.out.println("whIdx2 = " + whIdx2);
        System.out.println("whIdx1 = " + whIdx1);
    }



//        int locIdx_in = locIdx[0];
//        int locIdx_out = locIdx[1];
//
//        long gIdx1 = gIdx[2];
//        Location location = paramMap.mapParam(Location.class);
//       Goods goods = paramMap.mapParam(Goods.class);
//
//       long gIdx = goodsRepo.getReferenceById(gIdx;
//        String gIdxs[] = gIdx.substring(1, (locIdx.length() - 0)).split(",");
//
//        System.out.println("gIdx1 = " + gIdx1);
//
//
//            for (int i = 0; i < gIdxs.length; i++) {
//                Goods goods = goodsRepo.findById(Long.parseLong(gIdxs[i])).get();
//                if (!goods.getGState().equals(GState.RECEIVED)) {
//                    throw new MsgException(goods.getGBarcode() + "는 이미 입고검수한 자산입니다.");
//                }
//            }
//        }
//

        //출고 로케이션에 있던 자산들을 입고 로케이션으로 변경
//        Location location = paramMap.mapParam(Location.class);
//        Goods goods = paramMap.mapParam(Goods.class);

//        String locIdx = String.valueOf(locationRepo.findLocationByLocIdx(Integer.parseInt("locIdx")));
//        String locIdxs[] = locIdx.substring(1, (locIdx.length() - 0)).split(",");


//        System.out.println("location = " + location);

 

}
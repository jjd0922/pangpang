package com.newper.service;

import com.newper.constant.GState;
import com.newper.constant.PType1;
import com.newper.dto.ParamMap;
import com.newper.entity.Goods;
import com.newper.exception.MsgException;
import com.newper.mapper.GoodsMapper;
import com.newper.repository.GoodsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StockService {

    private final GoodsRepo goodsRepo;

    private final GoodsMapper goodsMapper;

    @Transactional
    public String beforeRelease(ParamMap paramMap){
        int cnt = 0;
        String g_idxs[] = paramMap.getString("GOODS_IDXS").split(",");
        for(int i=0; i<g_idxs.length; i++){
            Long G_IDX = Long.parseLong(g_idxs[i]);
            Goods goods = goodsRepo.getReferenceById(G_IDX);
            GState g_state = goods.getGState();
            if(g_state.equals(GState.STOCK)){
                goods.setGState(GState.BEFORE_RELEASE_REQ);
                goodsRepo.saveAndFlush(goods);
                cnt++;
            }
        }
        return cnt + " 건 요청완료";
    }


    @Transactional
    public boolean getBarcode(String barcode) {
        Goods goods = goodsRepo.findBygBarcode(barcode);
        List<Map<String, Object>> list = goodsMapper.selectBarcodeDataTable(barcode);
        boolean check = false;
        for(int i=0; i<list.size(); i++){
            Map<String, Object> map = list.get(i);
            String gBarcode = map.get("G_BARCODE")+"";
            if(barcode.equals(gBarcode)){
                goods.setGState(GState.BEFORE_RELEASE_REQ);
                goodsRepo.save(goods);
                check=true;
                break;
            }
        }

        return check;
    }
}



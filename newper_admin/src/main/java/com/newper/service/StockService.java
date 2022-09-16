package com.newper.service;

import com.newper.constant.GState;
import com.newper.dto.ParamMap;
import com.newper.entity.Goods;
import com.newper.repository.GoodsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {

    private final GoodsRepo goodsRepo;

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

}


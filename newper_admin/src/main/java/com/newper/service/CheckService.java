package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.*;
import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
import com.newper.repository.CheckGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CheckService {

    private final CheckGroupRepo checkGroupRepo;
    private final ChecksMapper checkMapper;
    private final GoodsMapper goodsMapper;

    /** 검수 그룹 등록*/
    @Transactional
    public void insertCheckGroup(ParamMap paramMap){
        CheckGroup checkGroup = paramMap.mapParam(CheckGroup.class);

        Company company = paramMap.mapParam(Company.class);
        checkGroup.setCompany(company);

        User user = paramMap.mapParam(User.class);
        checkGroup.setUser(user);

        User user2 = User
                .builder()
                .uIdx(paramMap.getInt("uIdx2"))
                .build();
        checkGroup.setUser2(user2);

        checkGroupRepo.save(checkGroup);


        //check goods
        List<Long> gIdx = paramMap.getListLong("gIdx");

        for (int i = 0; i < gIdx.size(); i++) {
            Goods goods = Goods
                    .builder()
                    .gIdx(gIdx.get(i))
                    .build();





            CheckGoods checkGoods = CheckGoods
                    .builder()
                    .goods(goods)
                    .checkGroup(checkGroup)
                    .cgsExpectedCost(Integer.parseInt(goodsMapper.selectGoodsByG_IDX(gIdx.get(i)).get("TOTAL_PROCESS_COST").toString()))
                    .cgsRealCost(0)
                    .cgsType(paramMap.get("cgsType").toString())
                    .cgsCount(checkMapper.countCheckGroupByGoods(gIdx.get(i)) + 1)
                    .build();
        }

        long ggt_idx = paramMap.getLong("ggt_idx");
        goodsMapper.deleteGoodsGroupTempByGGT_IDX(ggt_idx);
    }
}

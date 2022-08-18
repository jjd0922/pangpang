package com.newper.service;

import com.newper.constant.GiftState;
import com.newper.dto.ParamMap;
import com.newper.entity.Gift;
import com.newper.entity.GiftGroup;
import com.newper.exception.MsgException;
import com.newper.mapper.GiftMapper;
import com.newper.repository.GiftGroupRepo;
import com.newper.repository.GiftRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {

    private final GiftGroupRepo giftGroupRepo;
    private final GiftRepo giftRepo;
    private final GiftMapper giftMapper;

    /** GIFT_GROUP INSERT*/
    @Transactional
    public GiftGroup saveGiftGroup(ParamMap paramMap) {
        GiftGroup giftGroup = paramMap.mapParam(GiftGroup.class);
        giftGroup.setGiftgStartDate(LocalDate.parse(paramMap.getString("giftgEndDate")));
        giftGroup.setGiftgEndDate(LocalDate.parse(paramMap.getString("giftgStartDate")));
        GiftGroup savedGg = giftGroupRepo.save(giftGroup);
        return savedGg;
    }

    /** GIFT INSERT*/
    @Transactional
    public long saveGift(ParamMap paramMap) {
        GiftGroup savedGg = saveGiftGroup(paramMap);

        long giftgIdx = savedGg.getGiftgIdx();
        int giftgCnt = savedGg.getGiftgCnt();
        int round = 0;
        int cnt = 0;

        do {
            giftMapper.insertGift(giftgIdx, (giftgCnt - cnt), GiftState.WAITING, 16);
            cnt = giftMapper.countGiftByGiftgIdx(giftgIdx);
            round++;
        } while ((giftgCnt > cnt) && (round < 5));

        return giftgIdx;
    }

    @Transactional
    public void disposeGift(ParamMap paramMap) {
        String giftIdxs[] = paramMap.getString("giftIdxs").split(",");
        for (int i = 0; i < giftIdxs.length; i++) {
            Gift gift = giftRepo.findById(Long.parseLong(giftIdxs[i])).orElseThrow(() -> new MsgException("존재하지 않는 상품권입니다."));
            gift.setGiftState(GiftState.DISPOSAL);
        }
    }
}

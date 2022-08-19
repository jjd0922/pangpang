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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {

    private final GiftGroupRepo giftGroupRepo;
    private final GiftRepo giftRepo;
    private final GiftMapper giftMapper;

    /** GIFT INSERT. 훗날 중복된 상품권 코드로 생성수량만큼 상품권 코드가 생성되지 않을 수 있음. */
    @Transactional
    public long saveGift(ParamMap paramMap) {
        GiftGroup giftGroup = paramMap.mapParam(GiftGroup.class);

        giftGroup.setGiftgStartDate(LocalDate.parse(paramMap.getString("giftgEndDate")));
        giftGroup.setGiftgEndDate(LocalDate.parse(paramMap.getString("giftgStartDate")));
        GiftGroup savedGg = giftGroupRepo.save(giftGroup);

        long giftgIdx = savedGg.getGiftgIdx();
        int giftgCnt = savedGg.getGiftgCnt();
        int round = 0;
        int cnt = 0;

        do {
            giftMapper.insertGift(giftgIdx, (giftgCnt - cnt), GiftState.WAITING, 16);
            cnt = giftMapper.countGiftByGiftgIdx(giftgIdx);
            round++;
        } while ((giftgCnt > cnt) && (round < 5));

        if(giftgCnt > cnt){
            log.error("상품권 코드 생성 수량만큼 생성 안됨. 상품권 코드 길이 추가 필요 giftgIdx="+giftgIdx);
        }

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

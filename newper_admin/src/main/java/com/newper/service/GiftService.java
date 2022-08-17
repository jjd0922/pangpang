package com.newper.service;

import com.newper.constant.GiftState;
import com.newper.dto.ParamMap;
import com.newper.entity.Gift;
import com.newper.entity.GiftGroup;
import com.newper.exception.MsgException;
import com.newper.repository.GiftGroupRepo;
import com.newper.repository.GiftRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {

    private final GiftGroupRepo giftGroupRepo;
    private final GiftRepo giftRepo;

    /** GIFT_GROUP INSERT*/
    @Transactional
    public GiftGroup saveGiftGroup(ParamMap paramMap) {
        paramMap.put("giftgStartDate", LocalDate.parse(paramMap.getString("giftgStartDate")));
        paramMap.put("giftgEndDate", LocalDate.parse(paramMap.getString("giftgEndDate")));
        GiftGroup giftGroup = paramMap.mapParam(GiftGroup.class);
        GiftGroup savedGg = giftGroupRepo.save(giftGroup);
        return savedGg;
    }

    /** GIFT INSERT*/
    @Transactional
    public long saveGift(ParamMap paramMap) {
        GiftGroup savedGg = saveGiftGroup(paramMap);

        for (int i = 0; i < savedGg.getGiftgCnt(); i++) {
            Gift newGift = Gift.builder()
                    .giftState(GiftState.WAITING)
                    .giftGroup(savedGg)
                    .giftCode(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS"))) // 날짜로 임시생성
                    .build();
            giftRepo.save(newGift);
        }

        return savedGg.getGiftgIdx();
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

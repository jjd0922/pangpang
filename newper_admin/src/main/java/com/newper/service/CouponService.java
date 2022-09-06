package com.newper.service;

import com.newper.dto.ParamMap;
import com.newper.entity.CouponGroup;
import com.newper.entity.Shop;
import com.newper.mapper.CouponMapper;
import com.newper.repository.CouponGroupRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {

    private final CouponGroupRepo couponGroupRepo;
    private final CouponMapper couponMapper;

    /** 쿠폰그룹 등록 + 해당 그룹에 속한 쿠폰들 생성*/
    @Transactional
    public Long saveCoupon(ParamMap paramMap) {
        // 쿠폰그룹 생성
        CouponGroup couponGroup = paramMap.mapParam(CouponGroup.class);
        couponGroup.setCpgEndDate(LocalDate.parse(paramMap.getString("cpgEndDate")));
        couponGroup.setCpgStartDate(LocalDate.now()); // null이면 안되어서 임시로 일단 현재시각. 물어보기

        if (StringUtils.hasText(paramMap.getString("shopIdx"))) {
            couponGroup.setShop(Shop.builder().shopIdx(paramMap.getInt("shopIdx")).build());
        }

        CouponGroup savedCpg = couponGroupRepo.save(couponGroup);
        Long cpgIdx = savedCpg.getCpgIdx();
        couponMapper.insertGift(cpgIdx, savedCpg.getCpgCnt()); // coupon 생성

        return cpgIdx;
    }
}

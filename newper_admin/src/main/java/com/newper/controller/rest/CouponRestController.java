package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.CouponMapper;
import com.newper.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon/")
@RequiredArgsConstructor
public class CouponRestController {

    private final CouponMapper couponMapper;
    private final CouponService couponService;

    /**쿠폰생성관리 데이터테이블 조회*/
    @PostMapping("couponGroup.dataTable")
    public ReturnDatatable couponGroupDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("쿠폰생성관리");

        paramMap.multiSelect("cpgState");
        paramMap.multiSelect("cpgDiscountType");
        paramMap.multiSelect("cpgType");
        paramMap.multiSelect("cpgDuplicate");

        rd.setData(couponMapper.selectCouponGroupDatatable(paramMap.getMap()));
        rd.setRecordsTotal(couponMapper.countCouponGroupDatatable(paramMap.getMap()));
        return rd;
    }

    /**쿠폰그룹 신규등록*/
    @PostMapping("save.ajax")
    public ReturnMap saveCoupon(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        Long cpgIdx = couponService.saveCoupon(paramMap);
        rm.setMessage("등록완료");
        rm.setLocation("/coupon/detail/"+cpgIdx);
        return rm;
    }

    @PostMapping("coupon.dataTable")
    public ReturnDatatable couponDatatable(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("쿠폰리스트");
        rd.setData(couponMapper.selectCouponDatatable(paramMap.getMap()));
        rd.setRecordsTotal(couponMapper.countCouponDatatable(paramMap.getMap()));
        return rd;
    }
    
}

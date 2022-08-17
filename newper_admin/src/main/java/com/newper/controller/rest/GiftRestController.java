package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.GiftMapper;
import com.newper.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/gift/")
@RestController
@RequiredArgsConstructor
public class GiftRestController {

    private final GiftMapper giftMapper;
    private final GiftService giftService;

    @PostMapping("giftGroup.dataTable")
    public ReturnDatatable giftGroup(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("상품권관리");

        rd.setData(giftMapper.selectGiftGroupDataTable(paramMap.getMap()));
        rd.setRecordsTotal(giftMapper.countGiftGroupDataTable(paramMap.getMap()));
        return rd;
    }

    @PostMapping("gift.dataTable")
    public ReturnDatatable gift(ParamMap paramMap) {
        System.out.println("paramMap = " + paramMap);
        ReturnDatatable rd = new ReturnDatatable("상품권조회");

        rd.setData(giftMapper.selectGiftDataTable(paramMap.getMap()));
        rd.setRecordsTotal(giftMapper.countGiftDataTable(paramMap.getMap()));
        return rd;
    }

    /**상품권 등록*/
    @PostMapping("giftPop")
    public ReturnMap saveGift(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        long ggIdx = giftService.saveGift(paramMap);

        rm.setMessage("등록완료");
        rm.setLocation("/product/giftPop/"+ggIdx);
        return rm;
    }

    @PostMapping("disposal.ajax")
    public ReturnMap disposeGift(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        giftService.disposeGift(paramMap);
        rm.setMessage("변경완료");
        return rm;
    }
}

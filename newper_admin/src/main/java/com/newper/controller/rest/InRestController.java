package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.PoMapper;
import com.newper.service.GoodsService;
import com.newper.service.InService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/in/")
@RestController
@RequiredArgsConstructor
public class InRestController {

    private final PoMapper poMapper;
    private final GoodsService goodsService;
    private final InService inService;

    /** 입고등록 조회*/
    @PostMapping("in.dataTable")
    public ReturnDatatable in(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("입고등록");

        List<Map<String, Object>> data = poMapper.selectInDatatable(paramMap.getMap());
        int count = poMapper.countInDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }
    /** 입고등록 팝업에서 발주서 group by 상품 조회*/
    @PostMapping("poProduct.dataTable")
    public ReturnDatatable poProduct(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        inService.insertInGroup(paramMap.getInt("po_idx"));

        List<Map<String, Object>> data = poMapper.selectInPoProductDatatable(paramMap.getMap());
        rd.setData(data);

        return rd;
    }
    /** 입고등록 팝업에서 발주서 group by 상품 조회*/
    @PostMapping("po.dataTable")
    public ReturnDatatable po(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = poMapper.selectInPoDatatable(paramMap.getMap());
        rd.setData(data);

        return rd;
    }
    /** 입고등록 팝업에서 상품 그룹으로 입고 수량 자산 목록 조회*/
    @PostMapping("product.dataTable")
    public ReturnDatatable pp(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> data = poMapper.selectInProductDatatable(paramMap.getMap());
        int count = poMapper.countInProductDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }

    @PostMapping("incheck.dataTable")
    public ReturnDatatable incheck(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();


        return rd;
    }

    @PostMapping("goods.dataTable")
    public ReturnDatatable goods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();

        return rd;
    }
    /** 입고등록 발주서 상품 바코드 등록*/
    @PostMapping("po/barcode.ajax")
    public ReturnMap poBarcode(int p_idx,int po_idx, String barcode){
        ReturnMap rm = new ReturnMap();

        goodsService.insertGoods(p_idx, po_idx, barcode);

        return rm;
    }
}

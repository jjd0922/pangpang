package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;

import com.newper.mapper.ChecksMapper;
import com.newper.mapper.PoMapper;
import com.newper.service.CheckService;
import com.newper.service.GoodsService;
import com.newper.service.InService;
import com.newper.service.PoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/in/")
@RestController
@RequiredArgsConstructor
public class InRestController {

    private final PoMapper poMapper;
    private final GoodsService goodsService;
    private final InService inService;
    private final PoService poService;
    private final ChecksMapper checksMapper;
    private final CheckService checkService;


    /**
     * 입고등록 조회
     */
    @PostMapping("in.dataTable")
    public ReturnDatatable in(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("입고등록");

        List<Map<String, Object>> data = poMapper.selectInDatatable(paramMap.getMap());
        int count = poMapper.countInDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }

    /**
     * 입고등록 팝업에서 발주서 group by 상품 조회
     */
    @PostMapping("poProduct.dataTable")
    public ReturnDatatable poProduct(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String, Object>> data = poMapper.selectInPoProductDatatable(paramMap.getMap());
        rd.setData(data);
        return rd;
    }

    /**
     * 입고등록 팝업에서 발주서 group by 상품 조회
     */
    @PostMapping("po.dataTable")
    public ReturnDatatable po(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        List<Map<String, Object>> data = poMapper.selectInPoDatatable(paramMap.getMap());
        rd.setData(data);
        return rd;
    }

    /**
     * 입고등록 팝업에서 상품 그룹으로 입고 수량 자산 목록 조회
     */
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
        ReturnDatatable rd = new ReturnDatatable("입고검수");

        List<Map<String, Object>> list = checksMapper.selectCheckGroupDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(checksMapper.countCheckGroupDatatable(paramMap.getMap()));

        return rd;
    }

    @PostMapping("goods.dataTable")
    public ReturnDatatable goods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        
        
        return rd;
    }

    /**
     * 입고등록 발주서 상품 바코드 등록
     */
    @PostMapping("po/barcode.ajax")
    public ReturnMap poBarcode(int p_idx, int po_idx, String barcode) {
        ReturnMap rm = new ReturnMap();

        goodsService.insertGoods(p_idx, po_idx, barcode);

        return rm;
    }


    /** 입고등록 발주서 상품 바코드 삭제 */
    @PostMapping("po/barcode/delete.ajax")
    public ReturnMap barcodeDelete(long g_idx) {
        ReturnMap rm = new ReturnMap();

        goodsService.barcodeDelete(g_idx);
        rm.setMessage("삭제 완료");

        return rm;

    }

    /** 자산수령 */
    @PostMapping("{poIdx}/poComp.ajax")
    public ReturnMap productComp(@PathVariable int poIdx) {
        ReturnMap rm = new ReturnMap();
        poService.productComp(poIdx);
        rm.setMessage("자산수령 완료");

        return rm;

    }
    /** 강제 입고 완료 */
    @PostMapping("po/compulsion.ajax")
    public ReturnMap compulsion(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        inService.updateInGroup(paramMap);
        rm.setMessage("강제입고 완료");

        return rm;

    }

    /** 입고완료 */
    @PostMapping("inGroupSave.ajax")
    public ReturnMap inGroupSave(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        inService.updateInGroup(paramMap);
        rm.setMessage("입고 완료");

        return rm;

    }
    /** 영업검수 조회*/
    @PostMapping("checks.dataTable")
    public ReturnDatatable sale(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> list = checksMapper.selectChecksDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(checksMapper.countChecksDatatable(paramMap.getMap()));

        return rd;
    }

    /** 입고검수 작업요청취소 */
    @PostMapping("checkGroupStateUpdate.ajax")
    public ReturnMap checkGroupStateUpdate(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        inService.checkGroupStateUpdate(paramMap);
        return rm;
    }

    /** 입고검수 자산 검색 */
    @PostMapping("checkGoods.dataTable")
    public ReturnDatatable checkGoods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(checksMapper.selectCheckGoods(paramMap.getMap()));
        rd.setRecordsTotal(checksMapper.countCheckGroupDatatable(paramMap.getMap()));

        return rd;
    }

    /** 입고검수 자산 SPEC 세팅 */
    @PostMapping("checkGoodsSpecSet.ajax")
    public ReturnMap checkGoodsSpecSet(ParamMap paramMap, MultipartFile[] gFile) {
        ReturnMap rm = new ReturnMap();
        checkService.updateCheckGoodsSpec(paramMap, gFile);
        rm.setMessage("등록완료");
        return rm;
    }

    /** 발주매핑 */
    @PostMapping("poCheckMapping.ajax")
    public ReturnMap poCheckMapping(ParamMap paramMap, MultipartFile cgsFile) {
        ReturnMap rm = new ReturnMap();
        System.out.println("param: " + paramMap.getMap().entrySet());
        return rm;
    }
}



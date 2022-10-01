package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;

import com.newper.mapper.ChecksMapper;
import com.newper.mapper.GoodsMapper;
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
    private final GoodsMapper goodsMapper;


    /** 입고등록 조회 */
    @PostMapping("in.dataTable")
    public ReturnDatatable in(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("입고등록");
        paramMap.multiSelect("igState");
        List<Map<String, Object>> data = poMapper.selectInDatatable(paramMap.getMap());
        int count = poMapper.countInDatatable(paramMap.getMap());

        rd.setData(data);
        rd.setRecordsTotal(count);

        return rd;
    }

    /** 입고등록 팝업에서 발주서 group by 상품 조회 */
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

    @PostMapping("inCheck.dataTable")
    public ReturnDatatable inCheck(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable("입고검수");
        paramMap.multiSelect("cgState");
        List<Map<String, Object>> list = checksMapper.selectCheckGroupDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(checksMapper.countCheckGroupDatatable(paramMap.getMap()));

        return rd;
    }

    @PostMapping("inGoods.dataTable")
    public ReturnDatatable goods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(goodsMapper.selectInGoodsDataTable(paramMap.getMap()));
        rd.setRecordsTotal(goodsMapper.countInGoodsDataTable(paramMap.getMap()));
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
    public ReturnMap barcodeDelete(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        goodsService.barcodeDelete(paramMap);
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

    /** 입고완료 */
    @PostMapping("inGroupSave.ajax")
    public ReturnMap inGroupSave(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        inService.updateInGroup(paramMap);
        rm.setMessage("입고 완료");

        return rm;

    }
    /** 영업검수 조회 (발주단위) */
    @PostMapping("checks.dataTable")
    public ReturnDatatable sale(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable();

        List<Map<String, Object>> list = checksMapper.selectChecksDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(checksMapper.countChecksDatatable(paramMap.getMap()));

        return rd;
    }

    /** 입고검수 자산 검색 */
    @PostMapping("checkGoods.dataTable")
    public ReturnDatatable checkGoods(ParamMap paramMap) {
        ReturnDatatable rd = new ReturnDatatable();
        rd.setData(checksMapper.selectCheckGoods(paramMap.getMap()));
        rd.setRecordsTotal(checksMapper.countCheckGoods(paramMap.getMap()));

        return rd;
    }

    /** 입고검수 자산 정보 입력 */
    @PostMapping("saveInCheckReport.ajax")
    public ReturnMap saveInCheckReport(ParamMap paramMap, MultipartFile[] gFile) {
        ReturnMap rm = new ReturnMap();
        checkService.saveInCheckReport(paramMap, gFile);
        rm.setMessage("등록완료");
        return rm;
    }

    /** 영업검수 (발주) 자산 정보 확정 */
    @PostMapping("saveGoodsReport.ajax")
    public ReturnMap saveGoodsReport(ParamMap paramMap, MultipartFile[] gFile) {
        ReturnMap rm = new ReturnMap();
        checkService.saveGoodsReport(paramMap, gFile);
        rm.setMessage("등록완료");
        return rm;
    }

    /** 영업검수 (자산) 자산 정보 확정 */
    @PostMapping("saveCheckReport.ajax")
    public ReturnMap saveCheckReport(ParamMap paramMap, MultipartFile[] gFile) {
        ReturnMap rm = new ReturnMap();
        checkService.saveCheckReport(paramMap, gFile);
        rm.setMessage("등록완료");
        return rm;
    }

    /** 해당 발주의 실입고 상품 select */
    @PostMapping("selectReceivedByPo.ajax")
    public ReturnMap selectReceivedByPo(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        poMapper.selectReceivedByPo(paramMap.getMap());

        return rm;
    }

    /** 입고등록시 새상품 등록 */
    @PostMapping("insertInProduct.ajax")
    public ReturnMap insertInProduct(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        inService.insertInProduct(paramMap);
        return rm;
    }

    /** 입고 검수 상태값 수정 */
    @PostMapping("inCheckStateUpdate.ajax")
    public ReturnMap inCheckStateUpdate(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        inService.inCheckStateUpdate(paramMap);
        return rm;
    }
}



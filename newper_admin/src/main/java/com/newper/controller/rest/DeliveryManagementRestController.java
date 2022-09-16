package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.DeliveryMapper;
import com.newper.mapper.OrdersMapper;
import com.newper.service.OrderService;
import com.newper.service.DeliverytService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/deliveryManagement/")
@RequiredArgsConstructor
@RestController
public class DeliveryManagementRestController {

    private final DeliveryMapper deliveryMapper;
    private final DeliverytService deliverytService;


    private final OrderService orderService;

    private final OrdersMapper ordersMapper;
    /**배송관리 데이터테이블*/
    @PostMapping("delivery.dataTable")
    public ReturnDatatable delivery (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("배송관리");

        rd.setData(deliveryMapper.selectDeliveryDatatable(paramMap.getMap()));
        rd.setRecordsTotal(deliveryMapper.countDeliveryDatatable(paramMap.getMap()));
        return rd;
    }
    /**배송관리 데이터테이블2*/
    @PostMapping("delivery2.dataTable")
    public ReturnDatatable delivery2 (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("배송관리");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }
    /**설치관리 데이터테이블*/
    @PostMapping("installation.dataTable")
    public ReturnDatatable installation (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("설치관리");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**배송관리 송장통합팝업 데이터테이블*/
    @PostMapping("invoiceIntegrated.dataTable")
    public ReturnDatatable invoiceIntegrated (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("송장통합");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**배송관리 송장등록 데이터테이블*/
    @PostMapping("invoiceUpload.dataTable")
    public ReturnDatatable invoiceUpload (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("송장등록");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }
    /**배송관리 출고등록 데이터테이블*/
    @PostMapping("release1.dataTable")
    public ReturnDatatable release1 (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("출고등록 주문내역");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }

    /**배송관리 출고등록 데이터테이블*/
    @PostMapping("release2.dataTable")
    public ReturnDatatable release2 (ParamMap paramMap, HttpServletResponse response) {
        ReturnDatatable rd = new ReturnDatatable("출고등록 자산내역");

/*        rd.setData(consultationResultMapper.selectConsultationResultDatatable(paramMap.getMap()));
        rd.setRecordsTotal(consultationResultMapper.countConsultationResultDatatable(paramMap.getMap()));*/
        return rd;
    }


    /**배송관리 팝업 송장등록*/
    @PostMapping("insertInvoice.ajax")
    public ReturnMap insertInvoice(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        paramMap.put("ogIdx", paramMap.getList("ogIdxList[]"));
        System.out.println("~~~~~~~~~~~~~~"+paramMap.getMap());
        String insertInvoice = orderService.insertInvoice(paramMap);

        rm.setMessage(insertInvoice);

        return rm;
    }


    /**주문통합관리 상세 상품 데이터테이블*/
    @PostMapping("orderGoodsStock.dataTable")
    public ReturnDatatable a(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String,Object>> list = ordersMapper.selectGoodsStockDetailByOIdx(paramMap.getMap());
        returnDatatable.setData(list);
        returnDatatable.setRecordsTotal(list.size());

        return returnDatatable;
    }



    /**송장 업로드*/
    @PostMapping("deliveryUpload.ajax")
    public ReturnMap deliveryUpload(ParamMap paramMap, MultipartFile deliveryFile){
        ReturnMap rm = new ReturnMap();
        String res = deliverytService.deliveryUpload(paramMap, deliveryFile);
        if (res == "") {
            rm.put("result","송장등록 업로드 완료");
        } else {
            rm.put("result", res);
        }
        return rm;
    }
}

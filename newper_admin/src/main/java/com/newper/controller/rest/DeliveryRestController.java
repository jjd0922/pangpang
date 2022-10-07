package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.DeliveryNum;
import com.newper.mapper.DeliveryMapper;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.DeliveryNumRepo;
import com.newper.service.OrderService;
import com.newper.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/delivery/")
@RequiredArgsConstructor
@RestController
public class DeliveryRestController {

    private final DeliveryMapper deliveryMapper;
    private final DeliveryService deliveryService;
    private final DeliveryNumRepo deliveryNumRepo;


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

        rd.setData(deliveryMapper.selectDeliveryDatatable(paramMap.getMap()));
        rd.setRecordsTotal(deliveryMapper.countDeliveryDatatable(paramMap.getMap()));
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
        String res = deliveryService.deliveryUpload(paramMap, deliveryFile);
        if (res == "") {
            rm.put("result","송장등록 업로드 완료");
        } else {
            rm.put("result", res);
        }
        return rm;
    }

    /**설치확인서 등록*/
    @PostMapping("saveInstall.ajax")
    public ReturnMap saveInstall(ParamMap paramMap, MultipartFile DN_FILE){
        ReturnMap rm = new ReturnMap();

        System.out.println(paramMap.getMap());
        int count = paramMap.getString("idxs").split(",").length;
        int res = deliveryService.saveInstall(paramMap, DN_FILE);
        if(res==count){
            rm.setMessage("등록되었습니다.");
        }else{
            if(count==1){
                rm.setMessage("등록실패");
            }else{
                rm.setMessage(count+" 건 중 "+ res + "건 등록완료");
            }
        }

        return rm;
    }

    /**설치확인서 조회*/
    @PostMapping("deliveryNum.ajax")
    public ReturnMap deliveryNum(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        Long DN_IDX=paramMap.getLong("DN_IDX");
        Long OG_IDX=paramMap.getLong("OG_IDX");
        DeliveryNum deliveryNum = deliveryNumRepo.findById(DN_IDX).get();
        Map<String, Object> dnJson = deliveryNum.getDnJson();

        // 설치확인서 파일 n개일시 반복문 사용
        List<List<String>> list = (List<List<String>>) dnJson.get("files");
        List<String> lt = list.get(0);

        rm.put("idxs", OG_IDX);
        rm.put("DN_SCHEDULE", deliveryNum.getDnSchedule());
        rm.put("DN_DATE", deliveryNum.getDnDate());
        rm.put("DN_FILE_NAME", lt.get(1));
        rm.put("DN_FILE", lt.get(0));

        /** DN_COMPANY -> DN_COM_IDX */
//        rm.put("DN_COMPANY", deliveryNum.getDnCompany());
        rm.put("DN_MEMO", dnJson.get("memo"));

        return rm;
    }

    /**출고전(입금대기,주문완료,상품준비중) 상태의 주문*/
    @PostMapping("orders.dataTable")
    public ReturnDatatable orders(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String,Object>> list = ordersMapper.selectGoodsGsDetailByOIdxAndPType(paramMap.getMap());
        System.out.println(list);
        returnDatatable.setData(list);
        returnDatatable.setRecordsTotal(list.size());

        return returnDatatable;
    }

    /**배송 주문건 상세 상품 데이터테이블*/
    @PostMapping("orderGoodsDetail.dataTable")
    public ReturnDatatable orderGoodsDetail(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        paramMap.put("P_DEL_TYPE","DELIVERY");
        List<Map<String,Object>> list = ordersMapper.selectGoodsGsDetailByOIdxAndPType(paramMap.getMap());
        returnDatatable.setData(list);
        returnDatatable.setRecordsTotal(list.size());

        return returnDatatable;
    }

    @PostMapping("checkBarcode.ajax")
    public ReturnMap checkBarcode(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        System.out.println(paramMap.getMap());
        Map<String, Object> res = deliveryService.checkBarcode(paramMap.getMap());
        rm.put("gMap",res);
        return rm;
    }

    @PostMapping("together.ajax")
    public ReturnMap together(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        deliveryService.tegether(paramMap.getMap());
        return rm;
    }


    /** 배송반품관리 조회 */
    @PostMapping("return.dataTable")
    public ReturnDatatable returnDatatable(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        paramMap.put("P_DEL_TYPE","DELIVERY");
        returnDatatable.setData(ordersMapper.selectReturnDatatable(paramMap.getMap()));
        returnDatatable.setRecordsTotal(ordersMapper.countReturnDatatable(paramMap.getMap()));

        return returnDatatable;
    }
}

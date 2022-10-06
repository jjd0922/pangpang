package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.OrderGs;
import com.newper.entity.ShopProductOption;
import com.newper.mapper.OrdersMapper;
import com.newper.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/orders/")
@RestController
@RequiredArgsConstructor
public class OrdersRestController {

    private final OrdersMapper ordersMapper;
    private final OrderService orderService;

    /**통합주문관리 데이터테이블*/
    @PostMapping("orders.dataTable")
    public ReturnDatatable order(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        List<Map<String, Object>> order = ordersMapper.selectOrderDatatable(paramMap.getMap());
        int total = ordersMapper.countOrderDatatable(paramMap.getMap());
        returnDatatable.setData(order);
        returnDatatable.setRecordsTotal(total);

        return returnDatatable;
    }
    /**통합주문관리 대량주문등록 팝업 2.대량발주 데이터 테이블 */
    @PostMapping("bulk.dataTable")
    public ReturnDatatable bulk(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();

        return returnDatatable;
    }

    /**사방넷 주문수집*/
    @PostMapping("sabangOrder.ajax")
    public ReturnMap sabangOrder(String startDate, String endDate){
        ReturnMap rm = new ReturnMap();
        orderService.sabangOrder("20220816", "20220816");

        return  rm;
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




    /**주문상세 저장*/
    @PostMapping("orderSave.ajax")
    public ReturnMap orderSave(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        boolean check = false;
        for(Map.Entry<String, Object> entry : paramMap.entrySet()) {
            if(entry.getKey().contains("OG_COUPON_")) {
                check=true;
            }
        }
        if(!check){
            rm.setMessage("상품을 추가해주세요.");
            return rm;
        }
        Long res = orderService.orderSave(paramMap);
        if(res>0){
            rm.setMessage("저장되었습니다.");
        }
        return rm;
    }

    /**주문상세 수정*/
    @PostMapping("orderUpdate.ajax")
    public ReturnMap orderUpdate(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();
        Long res = orderService.orderUpdate(paramMap);
        if(res>0){
            rm.setMessage("수정되었습니다.");
        }
        return rm;
    }

    /**SHOP주문관리 데이터테이블*/
    @PostMapping("shopOrder.dataTable")
    public ReturnDatatable shopOrder(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();

        returnDatatable.setData(ordersMapper.selectOrderShopDatatable(paramMap.getMap()));
        returnDatatable.setRecordsTotal(ordersMapper.countOrderShopDatatable(paramMap.getMap()));

        return returnDatatable;
    }

    /**주문통합관리 팝업 송장등록*/
    @PostMapping("insertInvoice.ajax")
    public ReturnMap insertInvoice(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();

        paramMap.put("ogIdx", paramMap.getList("ogIdxList[]"));


        String insertInvoice = orderService.insertInvoice(paramMap);

        rm.setMessage(insertInvoice);

        return rm;
    }

    /** 교환/반품/AS 상태 자산 조회 */
    @PostMapping("service.dataTable")
    public ReturnDatatable service(ParamMap paramMap){
        ReturnDatatable returnDatatable = new ReturnDatatable();
        returnDatatable.setData(ordersMapper.selectServiceGoodsDatatable(paramMap.getMap()));
        returnDatatable.setRecordsTotal(ordersMapper.countServiceGoodsDatatable(paramMap.getMap()));

        return returnDatatable;
    }

    /** AS 검수 리포트 등록 (예외사항 필요해서 따로뺌) **/
    @PostMapping("asCheckReport.ajax")
    public ReturnMap asCheckReport(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        orderService.asCheckReport(paramMap);
        rm.setMessage("등록완료");
        return rm;
    }

    /** AS불가 처리 **/
    @PostMapping("asImpossible.ajax")
    public ReturnMap asImpossible(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        orderService.asImpossible(paramMap);
        return rm;
    }

    /** AS 상세 사유 둥록 **/
    @PostMapping("saveAsReason.ajax")
    public ReturnMap saveAsReason(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        orderService.saveAsReason(paramMap);
        rm.setMessage("상세사유 등록 완료");
        return rm;
    }

    /** 회수송장생성 */
    @PostMapping("saveDeliveryNumAs.ajax")
    public ReturnMap saveDeliveryNumAs(ParamMap paramMap) {
        ReturnMap rm = new ReturnMap();
        orderService.saveDeliveryNumAs(paramMap);
        rm.setMessage("송장생성 완료");
        return rm;
    }
}

package com.newper.controller.rest;

import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.mapper.ShopMapper;
import com.newper.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "/shop/")
@RestController
@RequiredArgsConstructor
public class ShopRestController {


    private final ShopService shopService;
    private final ShopMapper shopMapper;


    /**분양몰 등록&수정*/
    @PostMapping(value = {"{shopIdx}.ajax", "new.ajax"})
    public ReturnMap shopIdx(@PathVariable(required = false) Integer shopIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        if(shopIdx != null){
            paramMap.put("shopIdx",shopIdx);
            shopService.shopUpdate(paramMap);
            rm.setMessage("수정 완료");
        }else{
            shopService.shopSave(paramMap);
            rm.setMessage("생성 완료");
        }

        return rm;
    }
    /** 분양몰 삭제*/
    @PostMapping(value = "delete.ajax")
    public ReturnMap changeShopState(ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        List<String> shopIdxs = paramMap.getList("shopIdxs[]");
        List<String> shopStates = paramMap.getList("states[]");


        for (int i=0;i<shopIdxs.size();i++) {
            if(!shopStates.get(i).equals("OPEN")){
                shopService.changeShopState(Integer.parseInt(shopIdxs.get(i)));
            }
        }

        rm.setMessage("변경 완료");
        return rm;
    }
    /** 분양몰 디자인 update*/
    @PostMapping(value = "pop/design/{shopIdx}")
    public ReturnMap updateShopDesign(@PathVariable Integer shopIdx, ParamMap paramMap){
        ReturnMap rm = new ReturnMap();

        shopService.shopDesignUpdate(shopIdx, paramMap);

        rm.setMessage("변경 완료");
        return rm;
    }

    /**Shop DataTable*/
    @PostMapping("shop.dataTable")
    public ReturnDatatable shop(ParamMap paramMap){
        ReturnDatatable rd = new ReturnDatatable("분양몰 관리");
        List<Map<String, Object>> list = shopMapper.selectShopDatatable(paramMap.getMap());
        rd.setData(list);
        rd.setRecordsTotal(shopMapper.countShopDatatable(paramMap.getMap()));
        return rd;
    }


}

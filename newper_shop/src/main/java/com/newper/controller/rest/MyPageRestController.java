package com.newper.controller.rest;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.dto.ReturnDatatable;
import com.newper.dto.ReturnMap;
import com.newper.entity.Customer;
import com.newper.entity.Orders;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.CustomerRepo;
import com.newper.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myPage/")
public class MyPageRestController {
    private final OrdersMapper ordersMapper;

    @Autowired
    private ShopSession shopSession;

    private Orders orders;

    private final MypageService mypageService;

    private final CustomerRepo customerRepo;

    /** 전체주문내역 list */
    @PostMapping("/productList.ajax")
    public ReturnDatatable productList(ParamMap paramMap) {
        System.out.println(paramMap.getMap());
        ReturnDatatable rd = new ReturnDatatable("전체주문내역");
        paramMap.put("CU_IDX",shopSession.getIdx());
        int cu_idx = paramMap.getInt("CU_IDX");
        System.out.println("cu_idx = " + cu_idx);




//        rd.setData(ordersMapper.selectOrderGsListByCuIdx());
        return rd;
    }



    /** 전체주문내역 list */
    @PostMapping("/selectProduct.ajax")
    public ReturnDatatable selectProduct(ParamMap paramMap) {
        System.out.println(paramMap.getMap());
        ReturnDatatable rd = new ReturnDatatable("전체주문내역");
        paramMap.put("CU_IDX",shopSession.getIdx());
        paramMap.put("O_IDX",orders.getOIdx());
        int cu_idx = paramMap.getInt("CU_IDX");
        System.out.println("cu_idx = " + cu_idx);




//        rd.setData(ordersMapper.selectOrderGsListByCuIdx());
        return rd;
    }




    /** AS 등록 처리(뉴퍼마켓 구매제품) */
    @PostMapping(value = "createAS.ajax")
    public ReturnMap createAS(ParamMap paramMap, MultipartHttpServletRequest AS_FILE){
        ReturnMap rm = new ReturnMap();
        System.out.println(paramMap.getMap());

        System.out.println(AS_FILE.getFiles("AS_FILE").get(0).getOriginalFilename());
        StringBuffer sb= new StringBuffer();

        for(int i=0;i<AS_FILE.getFiles("AS_FILE").size();i++){
            sb.append(AS_FILE.getFiles("AS_FILE").get(i).getOriginalFilename().toString());
        }

        System.out.println(paramMap.getMap());

        long idx = mypageService.createAS(paramMap,AS_FILE);


        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }



    /** AS 등록 처리(다른 온라인몰 구매제품) */
    @PostMapping(value = "createAS2.ajax")
    public ReturnMap createAS2(ParamMap paramMap,MultipartHttpServletRequest AS_FILE) {
        ReturnMap rm = new ReturnMap();
        System.out.println(paramMap.getMap());

        System.out.println(AS_FILE.getFiles("AS_FILE").get(0).getOriginalFilename());
        StringBuffer sb= new StringBuffer();

        for(int i=0;i<AS_FILE.getFiles("AS_FILE").size();i++){
            sb.append(AS_FILE.getFiles("AS_FILE").get(i).getOriginalFilename().toString());
        }

        System.out.println(paramMap.getMap());

        long idx = mypageService.createAS(paramMap,AS_FILE);


        rm.setMessage("등록이 완료되었습니다.");

        return rm;
    }
}

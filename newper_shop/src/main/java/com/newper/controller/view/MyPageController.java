package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myPage/")
public class MyPageController {

    private final ShopRepo shopRepo;

    /** 마이쇼핑 메뉴 load*/
    @PostMapping("{menu}.load")
    public ModelAndView myPageMenu(@PathVariable String menu){
        ModelAndView mav = new ModelAndView("myPage/"+menu);

        return mav;
    }
    /** 나의 쇼핑내역 메뉴 load */
    @PostMapping("myOrder/{menu}.load")
    public ModelAndView myOrderMenu(@PathVariable String menu){
        ModelAndView mav = new ModelAndView("myPage/myOrder_menu :: " + menu);

        return mav;
    }
    /** 주문/배송조회 하위 메뉴 load */
    @PostMapping("myOrder/list/{menu}.load")
    public ModelAndView orders(@PathVariable(required = false) String menu){
        ModelAndView mav = new ModelAndView("myPage/myOrderProductList");
        String orderMenuTitle = "";
        List<Shop> shopList = new ArrayList<>();
        if(menu.equals("orders")){
            Shop shop = shopRepo.findById(1).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            shopList.add(shop);
            orderMenuTitle = "주문 접수";
        }else if(menu.equals("payment")){
            shopList = shopRepo.findAll();
            orderMenuTitle = "결제 완료";
        }else if(menu.equals("ready")){
            Shop shop = shopRepo.findById(2).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            shopList.add(shop);
            orderMenuTitle = "상품 준비중";
        }else if(menu.equals("delStart")){
            Shop shop = shopRepo.findById(3).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            shopList.add(shop);
            orderMenuTitle = "배송출발";
        }else if(menu.equals("delIng")){
            Shop shop = shopRepo.findById(4).orElseThrow(()-> new MsgException("ㄷㄷㄷ"));
            Shop shop2 = shopRepo.findById(3).orElseThrow(()-> new MsgException("www"));
            shopList.add(shop);
            shopList.add(shop2);
            orderMenuTitle = "배송중";
        }else if(menu.equals("delComplete")){
            orderMenuTitle = "배송완료";
        }else if(menu.equals("cancel")) {
            orderMenuTitle = "취소/교환/반품";
        }else if(menu.equals("all")){
            orderMenuTitle = "전체 주문 내역";
            shopList = shopRepo.findAll();
        }
        mav.addObject("shopList", shopList);
        mav.addObject("orderMenuTitle", orderMenuTitle);
        return mav;
    }
    /** AS접수 하위 메뉴 load */
    @PostMapping("myOrder/as/{menu}.load")
    public ModelAndView registAS(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myOrder_menu_AS :: " + menu);

        return mav;
    }

}

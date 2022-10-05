package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.CustomerRepo;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myPage/")
public class MyPageController {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;

    private final OrdersMapper ordersMapper;
    private final ShopRepo shopRepo;

    /** 마이쇼핑 메뉴(최상위) load*/
    @PostMapping("{menu}.load")
    public ModelAndView myPageMenu(@PathVariable String menu){
        ModelAndView mav = new ModelAndView("myPage/"+menu);

        return mav;
    }
    @PostMapping("modal/{modal}.load")
    public ModelAndView myPageModal(@PathVariable String modal){
        ModelAndView mav = new ModelAndView("myPage/myPage_common :: "+modal);

        return mav;
    }

    /** 나의 쇼핑내역 메뉴 load */
    @PostMapping("myOrder/{menu}.load")
    public ModelAndView myOrderMenu(@PathVariable String menu){
        ModelAndView mav = new ModelAndView("myPage/myOrder_menu :: " + menu);


        mav.addObject("CU_IDX",shopSession.getIdx());
        long cuIdx = shopSession.getIdx();
        List<Map<String, Object>> list = ordersMapper.selectOrderGsListByCuIdx(cuIdx);
        mav.addObject("orders",list);
        System.out.println("list = " + list);
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

    /** 정품등록 하위 메뉴 load */
    @PostMapping("myOrder/origin/{menu}.load")
    public ModelAndView registOrigin(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myOrder_menu_origin :: " + menu);

        return mav;
    }

    /** 쿠폰·적립금·상품권 메뉴 load */
    @PostMapping("myGift/{menu}.load")
    public ModelAndView myGiftMenu(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myGift_menu :: " + menu);

        return mav;
    }
    /** 상품권/예치금 하위 메뉴 load */
    @PostMapping("myGift/deposit/{menu}.load")
    public ModelAndView depositHistory(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myGift_menu_deposit :: " + menu);

        return mav;
    }

    /** 관심상품·재입고알림  메뉴 load */
    @PostMapping("myAlarm/{menu}.load")
    public ModelAndView myAlarmMenu(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myAlarm_menu :: " + menu);

        return mav;
    }

    /** 리뷰·문의내역·이벤트응모 메뉴 load */
    @PostMapping("myList/{menu}.load")
    public ModelAndView myListMenu(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myList_menu :: " + menu);

        if(menu.equals("eventWinnerModal")){
            mav.addObject("modalTitle", "이벤트 당첨자보기");
        }

        return mav;
    }

    /** 상품리뷰 하위 메뉴 load */
    @PostMapping("myList/review/{menu}.load")
    public ModelAndView myListReviewMenu(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myList_menu_review :: " + menu);
        if (menu.equals("possibleReview")) {
            mav.addObject("review_ogg", ordersMapper.selectOGGForReview(shopSession.getId(), shopSession.getShopIdx(), 1, 5));
        }
        return mav;
    }

    /** 문의내역 하위 메뉴 load */
    @PostMapping("myList/qna/{menu}.load")
    public ModelAndView myListQnaMenu(@PathVariable(required = false) String menu, ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("myPage/myList_menu_qna :: " + menu);

        if(menu.equals("qnaModal")){
            if(paramMap.containsKey("qnaIdx") && !paramMap.get("qnaIdx").equals("")){
                mav.addObject("modalTitle", "1:1문의 수정하기");
            }else{
                mav.addObject("modalTitle", "1:1문의");
            }
        }else if(menu.equals("qnaProductModal")){
            mav.addObject("modalTitle", "상품문의 수정하기");
        }
        return mav;
    }

    /** 회원정보관리 메뉴 load */
    @PostMapping("myInfo/{menu}.load")
    public ModelAndView myInfoMenu(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myInfo_menu :: " + menu);
        if (menu.equals("updateInfo")) {
            mav.addObject("customer",customerRepo.findByCuId(shopSession.getId()));
        }
        return mav;
    }


    /** 주문내역 상세보기 load*/
    @PostMapping("order/detail.load")
    public ModelAndView orderDetail(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myOrder_detail :: " + "orderDetail");

//        System.out.println("shopSession = " + shopSession);
//
//        Customer customer = customerRepo.findByCuId(shopSession.getId());

        return mav;
    }

    /** 주문내역 상세보기 load*/
    @PostMapping("order/receipt.load")
    public ModelAndView receiptDetail(@PathVariable(required = false) String menu) {
        ModelAndView mav = new ModelAndView("myPage/myOrder_detail :: " + "receiptDetail");

        return mav;
    }

}

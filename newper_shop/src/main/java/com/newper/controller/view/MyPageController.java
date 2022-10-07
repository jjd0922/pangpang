package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.entity.Company;
import com.newper.entity.Orders;
import com.newper.entity.Shop;
import com.newper.exception.MsgException;
import com.newper.mapper.CompanyMapper;
import com.newper.mapper.OrdersMapper;
import com.newper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Order.desc;

@RequiredArgsConstructor
@RestController
@RequestMapping("/myPage/")
public class MyPageController {

    @Autowired
    private ShopSession shopSession;

    private final CustomerRepo customerRepo;
    private final OrdersMapper ordersMapper;
    private final ShopRepo shopRepo;
    private final CompanyMapper companyMapper;
    private final ReviewRepo reviewRepo;
    private final QnaRepo qnaRepo;
    private final QnaSpRepo qnaSpRepo;

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
    public ModelAndView registAS(@PathVariable(required = false) String menu, ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("myPage/myOrder_menu_AS :: " + menu);
        System.out.println("check!!!!!!!!!!");
        if(menu.equals("asProductModal")) {
            mav.addObject("CU_IDX", shopSession.getIdx());
            long cuIdx = shopSession.getIdx();
            List<Map<String, Object>> list = ordersMapper.selectOrderGsListByCuIdx(cuIdx);
            mav.addObject("orders", list);
        }else if(menu.equals("otherAsCompanyModal")) {

            List<Map<String, Object>> comList = companyMapper.selectCompanyType();

            mav.addObject("company", comList);


            }else if(menu.equals("asProductModal2")) {

            Company company =paramMap.mapParam(Company.class);
            Orders orders = paramMap.mapParam(Orders.class);
            Integer comIdx = Integer.parseInt(paramMap.get("COM_IDX2").toString());

            String oName = String.valueOf(paramMap.get("AS_NAME"));
            String phone = paramMap.getString("AS_PHONE1")+paramMap.get("AS_PHONE2")+paramMap.get("AS_PHONE3");

            mav.addObject("COM_IDX2",comIdx);

            mav.addObject("AS_NAME", oName);

            mav.addObject("oPhone", phone);

            Map<String,Object> map = new HashMap<>();
            map.put("comIdx",comIdx);
            map.put("oName",oName);
            map.put("oPhone",phone);
            List<Map<String, Object>> pList = ordersMapper.selectOrderGsListByComIdx(map);

            mav.addObject("orders", pList);

            System.out.println("pList!!!!!!!!!!!!!!!!!!!!! = " + pList);

        }


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
    public ModelAndView myListReviewMenu(@PathVariable(required = false) String menu, ParamMap paramMap) {
        ModelAndView mav = new ModelAndView("myPage/myList_menu_review :: " + menu);
        if (menu.equals("possibleReview")) {
            mav.addObject("review_ogg", ordersMapper.selectOGGForReview(shopSession.getId(), shopSession.getShopIdx(), 1, 5));
        } else if (menu.equals("completeReview")) {
            mav.addObject("review", reviewRepo.findAllByCustomer(customerRepo.getReferenceById(shopSession.getIdx()),Sort.by(desc("rIdx"))));
        }
        if (menu.equals("reviewModal")) {
            if (paramMap.getString("insert").equals("true")) {
                mav.addObject("oggIdx", paramMap.getLong("oggIdx"));
            } else {
                mav.addObject("review", reviewRepo.findById(paramMap.getLong("rIdx")).orElseThrow(()-> new MsgException("존재하지 않는 리뷰입니다.")));
            }
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
                mav.addObject("qna", qnaRepo.findById(paramMap.getLong("qnaIdx")).orElseThrow(() -> new MsgException("존재하지 않는 1:1문의입니다.")));
            }else{
                mav.addObject("modalTitle", "1:1문의");
            }
        }else if(menu.equals("qnaProductModal")){
            mav.addObject("modalTitle", "상품문의 수정하기");
        }else if(menu.equals("qnaHistory")) {
            mav.addObject("qnaList", qnaRepo.findAllByCustomerOrderByQnaIdxDesc(customerRepo.getReferenceById(shopSession.getIdx())));
        }else if(menu.equals("productQnaHistory")){
            mav.addObject("qnaSpList", qnaSpRepo.findAllByCustomerOrderByQspIdxDesc(customerRepo.getReferenceById(shopSession.getIdx())));
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

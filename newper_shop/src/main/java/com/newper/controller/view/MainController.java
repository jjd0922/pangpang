package com.newper.controller.view;

import com.newper.dto.ParamMap;
import com.newper.mapper.IamportMapper;
import com.newper.repository.ShopRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ShopRepo shopRepo;
    private final IamportMapper iamportMapper;

    @GetMapping(value = { "", "index"})
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    /* mainMenu 메인메뉴*/
    @GetMapping(value = "newLaunch")
    public ModelAndView newLaunch(){
        ModelAndView mav = new ModelAndView("mainMenu/newLaunch");
        return mav;
    }
    @GetMapping(value = "best")
    public ModelAndView best(){
        ModelAndView mav = new ModelAndView("mainMenu/best");
        return mav;
    }
    @GetMapping(value = "timeSale")
    public ModelAndView timeSale(){
        ModelAndView mav = new ModelAndView("mainMenu/timeSale");
        return mav;
    }
    @GetMapping(value = "lastSecond")
    public ModelAndView lastSecond(){
        ModelAndView mav = new ModelAndView("mainMenu/lastSecond");
        return mav;
    }
    @GetMapping(value = "review")
    public ModelAndView review(){
        ModelAndView mav = new ModelAndView("mainMenu/review");
        return mav;
    }
    @GetMapping(value = "event")
    public ModelAndView event(){
        ModelAndView mav = new ModelAndView("mainMenu/event");
        return mav;
    }

    /* myPage 나의쇼핑정보 */
    @GetMapping(value = "myPage")
    public ModelAndView myPage(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("myPage/myPage");
        return mav;
    }

    /* custCenter 고객센터 */
    @RequestMapping(value = "custCenter", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView custCenter(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("custCenter/custCenter");

//        if(paramMap.containsKey("menu")){
//            mav.addObject("menu", paramMap.getString("menu"));
//        }else{
//            mav.addObject("menu", "faq");
//        }
        mav.addObject("menu","faq");

        return mav;
    }

    /* product - 상품 상세페이지 */
    @GetMapping(value = "product")
    public ModelAndView product(){
        ModelAndView mav = new ModelAndView("product/product");
        return mav;
    }

    /* product - 카테고리 대분류/중분류 목록페이지 */
    @GetMapping(value = "category")
    public ModelAndView category(){
        ModelAndView mav = new ModelAndView("product/category");
        return mav;
    }

    /* order 주문프로세서 - 장바구니 */
    @GetMapping(value = "cart")
    public ModelAndView cart(){
        ModelAndView mav = new ModelAndView("orderProcess/cart");
        return mav;
    }

    /* order 주문프로세서 - 주문/결제정보입력 */
    @GetMapping(value = "orderComplete")
    public ModelAndView orderComplete(){
        ModelAndView mav = new ModelAndView("orderProcess/orderComplete");
        return mav;
    }

    /* order 주문프로세서 - 주문완료 */
    @GetMapping(value = "order")
    public ModelAndView order(){
        ModelAndView mav = new ModelAndView("orderProcess/order");
        return mav;
    }

    /*테스트용 페이지*/
    @GetMapping(value = "mainMenu/index")
    public ModelAndView mainIndex(){
        ModelAndView mav = new ModelAndView("mainMenu/index");
        return mav;
    }

    @GetMapping(value = "iamportTest")
    public ModelAndView iamportTest(){
        ModelAndView mav = new ModelAndView("iamport/payment");

        mav.addObject("pgList", iamportMapper.selectIamportPgList());

        List<Map<String, Object>> mm = iamportMapper.selectIamportMethodMap();
        Map<Object, List<Map<String, Object>>> ippMap = mm.stream().collect(Collectors.groupingBy(map -> map.get("IPM_IPP_IDX")));
        mav.addObject("pgMethod", ippMap);

        return mav;
    }
}

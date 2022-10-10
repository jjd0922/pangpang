package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.dto.ParamMap;
import com.newper.mapper.IamportMapper;
import com.newper.repository.EventGroupRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    @Autowired
    private ShopSession shopSession;

    private final IamportMapper iamportMapper;
    private final EventGroupRepo eventGroupRepo;

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
    public ModelAndView event(ParamMap paramMap){
        ModelAndView mav = new ModelAndView("mainMenu/event");

        mav.addObject("fragType","event");
        mav.addObject("eventGroupList",
                eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrueAndEgCloseDateAfter(shopSession.getShopIdx(), LocalDate.now() ));
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

}

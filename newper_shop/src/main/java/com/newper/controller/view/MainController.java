package com.newper.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

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
    public ModelAndView myPage(){
        ModelAndView mav = new ModelAndView("myPage/myPage");
        return mav;
    }

    /* custCenter 고객센터 */
    @GetMapping(value = "custCenter")
    public ModelAndView custCenter(){
        ModelAndView mav = new ModelAndView("custCenter/custCenter");
        return mav;
    }

    /* product 상품상세 */
    @GetMapping(value = "product")
    public ModelAndView product(){
        ModelAndView mav = new ModelAndView("product/product");
        return mav;
    }

    /*테스트용 페이지*/
    @GetMapping(value = "mainMenu/index")
    public ModelAndView mainIndex(){
        ModelAndView mav = new ModelAndView("mainMenu/index");
        return mav;
    }
    @GetMapping(value = "test")
    public ModelAndView test(){
        ModelAndView mav = new ModelAndView("mainMenu/test");
        return mav;
    }
}

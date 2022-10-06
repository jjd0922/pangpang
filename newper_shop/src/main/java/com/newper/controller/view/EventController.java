package com.newper.controller.view;

import com.newper.component.ShopSession;
import com.newper.constant.EgMenu;
import com.newper.repository.EventGroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event/")
public class EventController {

    @Autowired
    private ShopSession shopSession;

    private final EventGroupRepo eventGroupRepo;

    @GetMapping("{egIdx}")
    public ModelAndView eventDetail(@PathVariable("egIdx") Long egIdx){
        ModelAndView mav = new ModelAndView("mainMenu/event_egIdx");

        mav.addObject("eventGroup",eventGroupRepo.findByegIdx(egIdx));
        return mav;
    }

    /** 이벤트 /기획전 menu load */
    @PostMapping("{menu}.ajax")
    public ModelAndView sortEvent(@PathVariable("menu") String menu){
        ModelAndView mav = new ModelAndView("part/banner :: banner_2");
        if(!menu.equals("all")){
            for(EgMenu egMenu : EgMenu.values()){
                if(menu.equals(egMenu.name())){
                    mav.addObject("eventGroupList", eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrueAndEgMenu(shopSession.getShopIdx(),egMenu));
                }
            }
        }else{
            mav.addObject("eventGroupList", eventGroupRepo.findEventGroupByShop_shopIdxAndEgStateTrue(shopSession.getShopIdx()));
        }
        return mav;
    }

}
